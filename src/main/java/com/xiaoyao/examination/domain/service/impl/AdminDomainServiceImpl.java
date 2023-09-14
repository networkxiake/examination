package com.xiaoyao.examination.domain.service.impl;

import com.xiaoyao.examination.domain.entity.Admin;
import com.xiaoyao.examination.domain.repository.AdminRepository;
import com.xiaoyao.examination.domain.service.AdminDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDomainServiceImpl implements AdminDomainService {
    private final AdminRepository adminRepository;

    @Override
    public Admin getLoginAdminByUsername(String username) {
        return adminRepository.getLoginAdminByUsername(username);
    }

    @Override
    public long countAdmin() {
        return adminRepository.countAdmin();
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
}
