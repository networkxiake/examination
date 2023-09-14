package com.xiaoyao.examination.service.impl;

import com.xiaoyao.examination.domain.entity.Admin;
import com.xiaoyao.examination.domain.service.AdminDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.properties.ExaminationProperties;
import com.xiaoyao.examination.service.AdminService;
import com.xiaoyao.examination.service.StorageService;
import com.xiaoyao.examination.util.SaltUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminDomainService adminDomainService;
    private final StorageService storageService;
    private final RedissonClient redissonClient;
    private final ExaminationProperties examinationProperties;

    @PostConstruct
    public void init() {
        // 创建初始的运营账户
        if (adminDomainService.countAdmin() == 0) {
            RLock lock = redissonClient.getLock("init-admin");
            if (lock.tryLock()) {
                try {
                    if (adminDomainService.countAdmin() == 0) {
                        Admin admin = new Admin();
                        admin.setUsername(examinationProperties.getInitAdminUsername());
                        String salt = SaltUtil.generate();
                        admin.setSalt(salt);
                        admin.setPassword(SaltUtil.encrypt(examinationProperties.getInitAdminPassword(), salt));
                        admin.setName(examinationProperties.getInitAdminName());
                        admin.setPhoto(storageService.getDefaultPhotoPath());
                        admin.setCreateTime(LocalDateTime.now());
                        adminDomainService.createInitAdmin(admin);
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Override
    public Admin login(String username, String password) {
        Admin admin = adminDomainService.getLoginAdminByUsername(username);
        if (admin == null || !admin.getPassword().equals(SaltUtil.encrypt(password, admin.getSalt()))) {
            throw new ExaminationException(ErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }

        admin.setSalt(null);
        admin.setPassword(null);
        admin.setPhoto(storageService.getPathUrl(admin.getPhoto()));
        return admin;
    }

    @Override
    public void changePassword(long userId, String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            throw new ExaminationException(ErrorCode.OLD_AND_NEW_PASSWORD_SAME);
        }

        Admin admin = adminDomainService.getSaltAndPasswordById(userId);
        if (!admin.getPassword().equals(SaltUtil.encrypt(oldPassword, admin.getSalt()))) {
            throw new ExaminationException(ErrorCode.OLD_PASSWORD_ERROR);
        }

        admin.setId(userId);
        String salt = SaltUtil.generate();
        admin.setSalt(salt);
        admin.setPassword(SaltUtil.encrypt(newPassword, salt));
        adminDomainService.updateAdmin(admin);
    }
}
