package com.xiaoyao.examination.user.service;

import cn.hutool.core.util.IdUtil;
import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.common.interfaces.storage.StorageService;
import com.xiaoyao.examination.common.interfaces.storage.response.ApplyUploadPhotoResponse;
import com.xiaoyao.examination.common.interfaces.user.AdminService;
import com.xiaoyao.examination.common.interfaces.user.response.AdminLoginResponse;
import com.xiaoyao.examination.common.interfaces.user.response.SearchAdminResponse;
import com.xiaoyao.examination.user.domain.entity.Admin;
import com.xiaoyao.examination.user.domain.service.AdminDomainService;
import com.xiaoyao.examination.user.properties.ExaminationUserProperties;
import com.xiaoyao.examination.user.util.SaltUtil;
import jakarta.annotation.PostConstruct;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@DubboService
public class AdminServiceImpl implements AdminService {
    private final AdminDomainService adminDomainService;
    private final RedissonClient redissonClient;
    private final ExaminationUserProperties properties;

    @DubboReference
    private StorageService storageService;

    public AdminServiceImpl(AdminDomainService adminDomainService, RedissonClient redissonClient,
                            ExaminationUserProperties properties) {
        this.adminDomainService = adminDomainService;
        this.redissonClient = redissonClient;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        // 创建初始的运营账户
        if (adminDomainService.countAdmin() == 0) {
            RLock lock = redissonClient.getLock("init-admin");
            if (lock.tryLock()) {
                try {
                    if (adminDomainService.countAdmin() == 0) {
                        Admin admin = new Admin();
                        admin.setUsername(properties.getInitAdminUsername());
                        String salt = SaltUtil.generate();
                        admin.setSalt(salt);
                        admin.setPassword(SaltUtil.encrypt(properties.getInitAdminPassword(), salt));
                        admin.setName(properties.getInitAdminName());
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
    public AdminLoginResponse login(String username, String password) {
        Admin admin = adminDomainService.getLoginAdminByUsername(username);
        if (admin == null || !admin.getPassword().equals(SaltUtil.encrypt(password, admin.getSalt()))) {
            throw new ExaminationException(ErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }

        AdminLoginResponse response = new AdminLoginResponse();
        response.setId(admin.getId());
        System.out.println(admin.getUsername());
        response.setInitAdmin(admin.getUsername().equals(properties.getInitAdminUsername()));
        response.setName(admin.getName());
        response.setPhoto(storageService.getPathDownloadingUrl(admin.getPhoto()));
        return response;
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
    public ApplyUploadPhotoResponse applyUploadPhoto(String suffix) {
        List<String> strings = storageService.applySpaceForPhoto(IdUtil.fastSimpleUUID() + "." + suffix);
        ApplyUploadPhotoResponse response = new ApplyUploadPhotoResponse();
        response.setPath(strings.get(0));
        response.setUrl(strings.get(1));
        return response;
    }

    @Override
    public String updatePhoto(long userId, String path) {
        storageService.changeReference(adminDomainService.getPhotoById(userId), path);

        Admin admin = new Admin();
        admin.setId(userId);
        admin.setPhoto(path);
        adminDomainService.updateAdmin(admin);

        return storageService.getPathDownloadingUrl(path);
    }


    @Override
    public void createAdmin(String username, String password, String name) {
        adminDomainService.createAdmin(username, password, name, storageService.getDefaultPhotoPath());
    }

    @Override
    public void deleteAdmin(List<Long> ids) {
        List<String> paths = adminDomainService.deleteAdmin(ids);
        storageService.releaseSpace(paths);
    }

    @Override
    public SearchAdminResponse searchAdmin(long page, long size, String name) {
        long[] total = new long[1];
        List<SearchAdminResponse.Admin> admins = new ArrayList<>();
        adminDomainService.searchAdmin(page, size, name, total).forEach(item -> {
            SearchAdminResponse.Admin admin = new SearchAdminResponse.Admin();
            admin.setId(String.valueOf(item.getId()));
            admin.setUsername(item.getUsername());
            admin.setName(item.getName());
            admin.setPhoto(storageService.getPathDownloadingUrl(item.getPhoto()));
            admin.setCreateTime(item.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            admins.add(admin);
        });

        SearchAdminResponse response = new SearchAdminResponse();
        response.setTotal(total[0]);
        response.setResults(admins);
        return response;
    }
}
