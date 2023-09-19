package com.xiaoyao.examination.domain.repository;

import com.xiaoyao.examination.domain.entity.Admin;

import java.util.List;

public interface AdminRepository {
    Admin getLoginAdminByUsername(String username);

    long countAdmin(Long id);

    void createInitAdmin(Admin admin);

    Admin getSaltAndPasswordById(long id);

    void updateAdmin(Admin admin);

    String getPhotoById(long id);

    void createAdmin(Admin admin);

    boolean isUsernameExist(String username);

    void deleteAdmin(List<Long> ids, String initAdminUsername);

    List<Admin> searchAdmin(long page, long size, String name, long[] total, String initAdminUsername);

    List<String> getPhotoByAdminIds(List<Long> ids, String initAdminUsername);
}
