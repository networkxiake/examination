package com.xiaoyao.examination.user.domain.service.impl;

import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.user.domain.entity.User;
import com.xiaoyao.examination.user.domain.repository.UserRepository;
import com.xiaoyao.examination.user.domain.service.UserDomainService;
import org.springframework.stereotype.Service;

@Service
public class UserDomainServiceImpl implements UserDomainService {
    private final UserRepository userRepository;

    public UserDomainServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void create(User user) {
        if (userRepository.countByPhone(user.getPhone()) == 1) {
            throw new ExaminationException(ErrorCode.USER_ALREADY_EXIST);
        }
        userRepository.create(user);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public String getPhoto(long userId) {
        return userRepository.getPhoto(userId);
    }
}
