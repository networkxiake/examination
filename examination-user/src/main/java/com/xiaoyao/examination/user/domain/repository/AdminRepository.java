package com.xiaoyao.examination.user.domain.repository;

import com.xiaoyao.examination.user.domain.entity.Admin;

import java.util.List;

public interface AdminRepository {
    long countAdmin(Long id);

    List<Admin> findAdminListForSearch(long page, long size, String name, long[] total, String initAdminUsername);

    Admin findAdminForLogin(String username);

    Admin findAdminForChangePassword(long id);

    String getPhoto(long id);

    List<String> getPhotoInIds(List<Long> ids, String initAdminUsername);

    boolean isExistUsername(String username);

    void save(Admin admin);

    void delete(List<Long> ids, String initAdminUsername);

    void update(Admin admin);
}
