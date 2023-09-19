package com.xiaoyao.examination.service;

import com.xiaoyao.examination.domain.entity.User;

public interface UserService {
    void sendVerificationCode(String phone);

    User login(String phone, String code);

    String confirmPhoto(long userId, String path);
}
