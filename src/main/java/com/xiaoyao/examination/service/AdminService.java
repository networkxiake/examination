package com.xiaoyao.examination.service;

import com.xiaoyao.examination.controller.dto.admin.SearchAdminDTO;
import com.xiaoyao.examination.domain.entity.Admin;

import java.util.List;

public interface AdminService {
    Admin login(String username, String password);

    void changePassword(long userId, String oldPassword, String newPassword);

    String changePhoto(long userId, String path);

    void createAdmin(String username, String password, String name);

    void deleteAdmin(List<Long> ids);

    SearchAdminDTO searchAdmin(long page, long size, String name);
}
