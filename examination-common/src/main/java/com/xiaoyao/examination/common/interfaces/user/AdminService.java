package com.xiaoyao.examination.common.interfaces.user;

import com.xiaoyao.examination.common.interfaces.storage.response.ApplyUploadPhotoResponse;
import com.xiaoyao.examination.common.interfaces.user.response.AdminLoginResponse;
import com.xiaoyao.examination.common.interfaces.user.response.SearchAdminResponse;

import java.util.List;

public interface AdminService {
    AdminLoginResponse login(String username, String password);

    void changePassword(long userId, String oldPassword, String newPassword);

    ApplyUploadPhotoResponse applyUploadPhoto(String suffix);

    String updatePhoto(long userId, String path);

    void createAdmin(String username, String password, String name);

    void deleteAdmin(List<Long> ids);

    SearchAdminResponse searchAdmin(long page, long size, String name);
}
