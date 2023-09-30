package com.xiaoyao.examination.user.domain.repository;


import com.xiaoyao.examination.user.domain.entity.User;

public interface UserRepository {
    User findByPhone(String phone);

    long countByPhone(String phone);

    void create(User user);

    void update(User user);

    String getPhoto(long userId);

    User findById(long id);
}
