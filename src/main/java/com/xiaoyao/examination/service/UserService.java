package com.xiaoyao.examination.service;

import com.xiaoyao.examination.controller.dto.user.UserProfileDTO;
import com.xiaoyao.examination.domain.entity.User;

public interface UserService {
    void sendVerificationCode(String phone);

    User login(String phone, String code);

    String confirmPhoto(long userId, String path);

    UserProfileDTO profile(long userId);

    void updateProfile(long userId, String name, String gender);

    void updatePhone(long loginId, String phone, String code);
}
