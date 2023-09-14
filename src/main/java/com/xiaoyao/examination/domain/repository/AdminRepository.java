package com.xiaoyao.examination.domain.repository;

import com.xiaoyao.examination.domain.entity.Admin;

public interface AdminRepository {
    Admin getLoginAdminByUsername(String username);

    long countAdmin();

    void createInitAdmin(Admin admin);

    Admin getSaltAndPasswordById(long id);

    void updateAdmin(Admin admin);

    String getPhotoById(long id);
}
