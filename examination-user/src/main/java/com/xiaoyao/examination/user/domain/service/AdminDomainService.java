package com.xiaoyao.examination.user.domain.service;

import com.xiaoyao.examination.user.domain.entity.Admin;

import java.util.List;

public interface AdminDomainService {
    Admin getLoginAdminByUsername(String username);

    long countAdmin();

    void createInitAdmin(Admin admin);

    Admin getSaltAndPasswordById(long id);

    void updateAdmin(Admin admin);

    String getPhotoById(long id);

    boolean isInitAdmin(long id);

    void createAdmin(String username, String password, String name, String photo);

    List<String> deleteAdmin(List<Long> ids);

    List<Admin> searchAdmin(long page, long size, String name, long[] total);
}
