package com.xiaoyao.examination.user.domain.repository;

import com.xiaoyao.examination.user.domain.entity.User;

public interface UserRepository {
    long countPhone(String phone);

    User findUserForLogin(String phone);

    User findUserForProfile(long id);

    String getPhoto(long id);

    void save(User user);

    void update(User user);
}
