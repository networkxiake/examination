package com.xiaoyao.examination.domain.repository;

import com.xiaoyao.examination.domain.entity.User;

public interface UserRepository {
    User findByPhone(String phone);

    long countByPhone(String phone);

    void create(User user);

    void update(User user);

    String getPhoto(long userId);

    User findById(long id);
}
