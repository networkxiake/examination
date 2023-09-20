package com.xiaoyao.examination.domain.service.impl;

import com.xiaoyao.examination.domain.entity.User;
import com.xiaoyao.examination.domain.repository.UserRepository;
import com.xiaoyao.examination.domain.service.UserDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService {
    private final UserRepository userRepository;

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
