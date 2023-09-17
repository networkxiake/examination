package com.xiaoyao.examination.domain.service;

import com.xiaoyao.examination.domain.entity.User;

public interface UserDomainService {
    User findByPhone(String phone);

    void create(User user);

    void update(User user);

    String getPhoto(long userId);
}
