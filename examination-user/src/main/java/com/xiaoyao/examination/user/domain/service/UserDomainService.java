package com.xiaoyao.examination.user.domain.service;

import com.xiaoyao.examination.user.domain.entity.User;

public interface UserDomainService {
    User findByPhone(String phone);

    User findById(long id);

    void create(User user);

    void update(User user);

    String getPhoto(long userId);
}
