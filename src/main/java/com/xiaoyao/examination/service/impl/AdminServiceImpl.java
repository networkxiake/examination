package com.xiaoyao.examination.service.impl;

import com.xiaoyao.examination.controller.dto.admin.SearchAdminDTO;
import com.xiaoyao.examination.domain.entity.Admin;
import com.xiaoyao.examination.domain.service.AdminDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.properties.ExaminationProperties;
import com.xiaoyao.examination.service.AdminService;
import com.xiaoyao.examination.service.StorageService;
import com.xiaoyao.examination.service.event.FileChangedEvent;
import com.xiaoyao.examination.util.SaltUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminDomainService adminDomainService;
    private final StorageService storageService;
    private final RedissonClient redissonClient;
    private final ExaminationProperties examinationProperties;
    private final ApplicationEventMulticaster eventMulticaster;

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

    @Override
    public String changePhoto(long userId, String path) {
        String oldPath = adminDomainService.getPhotoById(userId);

        Admin admin = new Admin();
        admin.setId(userId);
        admin.setPhoto(path);
        adminDomainService.updateAdmin(admin);
        eventMulticaster.multicastEvent(new FileChangedEvent(oldPath, path));
        return storageService.getPathUrl(path);
    }

    @Override
    public void createAdmin(String username, String password, String name) {
        adminDomainService.createAdmin(username, password, name, storageService.getDefaultPhotoPath());
    }

    @Override
    public void deleteAdmin(List<Long> ids) {
        adminDomainService.deleteAdmin(ids);
    }

    @Override
    public SearchAdminDTO searchAdmin(long page, long size, String name) {
        long[] total = new long[1];
        List<SearchAdminDTO.Admin> admins = new ArrayList<>();
        adminDomainService.searchAdmin(page, size, name, total).forEach(item -> {
            SearchAdminDTO.Admin admin = new SearchAdminDTO.Admin();
            admin.setId(String.valueOf(item.getId()));
            admin.setUsername(item.getUsername());
            admin.setName(item.getName());
            admin.setPhoto(storageService.getPathUrl(item.getPhoto()));
            admin.setCreateTime(item.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            admins.add(admin);
        });

        SearchAdminDTO dto = new SearchAdminDTO();
        dto.setTotal(total[0]);
        dto.setResults(admins);
        return dto;
    }
}
