package com.xiaoyao.examination.service;

import com.xiaoyao.examination.domain.entity.User;

public interface UserService {
    void sendVerificationCode(String phone);

    User login(String phone, String code);

    void changePhoto(long userId, String path);
}
