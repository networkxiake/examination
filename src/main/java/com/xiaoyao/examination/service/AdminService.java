package com.xiaoyao.examination.service;

import com.xiaoyao.examination.domain.entity.Admin;

public interface AdminService {
    Admin login(String username, String password);

    void changePassword(long userId, String oldPassword, String newPassword);

    void changePhoto(long userId, String path);
}
