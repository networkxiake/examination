package com.xiaoyao.examination.domain.service;

import com.xiaoyao.examination.domain.entity.Admin;

public interface AdminDomainService {
    Admin getLoginAdminByUsername(String username);

    long countAdmin();

    void createInitAdmin(Admin admin);

    Admin getSaltAndPasswordById(long id);

    void updateAdmin(Admin admin);
}
