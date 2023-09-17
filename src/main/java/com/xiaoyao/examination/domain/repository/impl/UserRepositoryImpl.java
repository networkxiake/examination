package com.xiaoyao.examination.domain.repository.impl;

import com.xiaoyao.examination.domain.entity.User;
import com.xiaoyao.examination.domain.mapper.UserMapper;
import com.xiaoyao.examination.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserMapper userMapper;

    @Override
    public User findByPhone(String phone) {
        return userMapper.selectOne(lambdaQuery(User.class)
                .select(User::getId,
                        User::getName,
                        User::getPhoto)
                .eq(User::getPhone, phone));
    }

    @Override
    public long countByPhone(String phone) {
        return userMapper.selectCount(lambdaQuery(User.class)
                .eq(User::getPhone, phone));
    }

    @Override
    public void create(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public String getPhoto(long userId) {
        return userMapper.selectOne(lambdaQuery(User.class)
                        .select(User::getPhoto)
                        .eq(User::getId, userId))
                .getPhoto();
    }
}
