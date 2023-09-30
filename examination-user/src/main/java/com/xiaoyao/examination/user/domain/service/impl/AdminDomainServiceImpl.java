package com.xiaoyao.examination.user.domain.service.impl;

import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.user.domain.entity.Admin;
import com.xiaoyao.examination.user.domain.repository.AdminRepository;
import com.xiaoyao.examination.user.domain.service.AdminDomainService;
import com.xiaoyao.examination.user.properties.ExaminationUserProperties;
import com.xiaoyao.examination.user.util.SaltUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminDomainServiceImpl implements AdminDomainService {
    private final AdminRepository adminRepository;
    private final ExaminationUserProperties properties;

    public AdminDomainServiceImpl(AdminRepository adminRepository, ExaminationUserProperties properties) {
        this.adminRepository = adminRepository;
        this.properties = properties;
    }

    @Override
    public Admin getLoginAdminByUsername(String username) {
        return adminRepository.findAdminForLogin(username);
    }

    @Override
    public long countAdmin() {
        return adminRepository.countAdmin(null);
    }

    @Override
    public void createInitAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public Admin getSaltAndPasswordById(long id) {
        return adminRepository.findAdminForChangePassword(id);
    }

    @Override
    public void updateAdmin(Admin admin) {
        adminRepository.update(admin);
    }

    @Override
    public String getPhotoById(long id) {
        return adminRepository.getPhoto(id);
    }

    @Override
    public boolean isInitAdmin(long id) {
        return adminRepository.countAdmin(id) == 1;
    }

    @Override
    public void createAdmin(String username, String password, String name, String photo) {
        if (adminRepository.isExistUsername(username)) {
            throw new ExaminationException(ErrorCode.USERNAME_EXIST);
        }
        Admin admin = new Admin();
        admin.setUsername(username);
        String salt = SaltUtil.generate();
        admin.setSalt(salt);
        admin.setPassword(SaltUtil.encrypt(password, salt));
        admin.setName(name);
        admin.setPhoto(photo);
        admin.setCreateTime(LocalDateTime.now());
        adminRepository.save(admin);
    }

    @Override
    public List<String> deleteAdmin(List<Long> ids) {
        List<String> photos = adminRepository.getPhotoInIds(ids, properties.getInitAdminUsername());
        adminRepository.delete(ids, properties.getInitAdminUsername());
        return photos;
    }

    @Override
    public List<Admin> searchAdmin(long page, long size, String name, long[] total) {
        return adminRepository.findAdminListForSearch(page, size, name, total, properties.getInitAdminUsername());
    }
}
