package com.xiaoyao.examination.domain.service.impl;

import com.xiaoyao.examination.domain.entity.Admin;
import com.xiaoyao.examination.domain.repository.AdminRepository;
import com.xiaoyao.examination.domain.service.AdminDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.properties.ExaminationProperties;
import com.xiaoyao.examination.util.SaltUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDomainServiceImpl implements AdminDomainService {
    private final AdminRepository adminRepository;
    private final ExaminationProperties examinationProperties;

    @Override
    public Admin getLoginAdminByUsername(String username) {
        return adminRepository.getLoginAdminByUsername(username);
    }

    @Override
    public long countAdmin() {
        return adminRepository.countAdmin(null);
    }

    @Override
    public void createInitAdmin(Admin admin) {
        adminRepository.createInitAdmin(admin);
    }

    @Override
    public Admin getSaltAndPasswordById(long id) {
        return adminRepository.getSaltAndPasswordById(id);
    }

    @Override
    public void updateAdmin(Admin admin) {
        adminRepository.updateAdmin(admin);
    }

    @Override
    public String getPhotoById(long id) {
        return adminRepository.getPhotoById(id);
    }

    @Override
    public boolean isInitAdmin(long id) {
        return adminRepository.countAdmin(id) == 1;
    }

    @Override
    public void createAdmin(String username, String password, String name, String photo) {
        if (adminRepository.isUsernameExist(username)) {
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
        adminRepository.createAdmin(admin);
    }

    @Override
    public void deleteAdmin(List<Long> ids) {
        adminRepository.deleteAdmin(ids, examinationProperties.getInitAdminUsername());
    }

    @Override
    public List<Admin> searchAdmin(long page, long size, String name, long[] total) {
        return adminRepository.searchAdmin(page, size, name, total, examinationProperties.getInitAdminUsername());
    }
}
