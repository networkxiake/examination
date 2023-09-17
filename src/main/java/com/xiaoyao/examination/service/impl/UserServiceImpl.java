package com.xiaoyao.examination.service.impl;

import com.xiaoyao.examination.domain.entity.User;
import com.xiaoyao.examination.domain.service.UserDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.service.StorageService;
import com.xiaoyao.examination.service.UserService;
import com.xiaoyao.examination.service.event.FileChangedEvent;
import com.xiaoyao.examination.util.VerificationCodeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final static String VERIFICATION_CODE_PREFIX = "verification-code:";
    private final static String VERIFICATION_CODE_IP_PREFIX = "verification-code-ip:";

    private final UserDomainService userDomainService;
    private final StringRedisTemplate redisTemplate;
    private final VerificationCodeUtil verificationCodeUtil;
    private final StorageService storageService;
    private final HttpServletRequest request;
    private final ApplicationEventMulticaster eventMulticaster;

    @Override
    public void sendVerificationCode(String phone) {
        // TODO 获取真实IP
        String ip = request.getRemoteAddr();

        // 防止频繁发送验证码
        if (Boolean.TRUE.equals(redisTemplate.hasKey(VERIFICATION_CODE_IP_PREFIX + ip))) {
            throw new ExaminationException(ErrorCode.VERIFICATION_CODE_SEND_TOO_FREQUENTLY);
        }
        redisTemplate.opsForValue().set(VERIFICATION_CODE_IP_PREFIX + ip, "1", 1, TimeUnit.MINUTES);

        String code = verificationCodeUtil.generate();
        verificationCodeUtil.send(phone, code);
        redisTemplate.opsForHash().putAll(VERIFICATION_CODE_PREFIX + phone, Map.of(
                "code", code,
                "remain", "5"
        ));
        redisTemplate.expire(VERIFICATION_CODE_PREFIX + phone, 5, TimeUnit.MINUTES);
    }

    @Override
    public User login(String phone, String code) {
        String key = VERIFICATION_CODE_PREFIX + phone;
        List<Object> values = redisTemplate.opsForHash().multiGet(key, List.of("code", "remain"));
        if (values.get(0) == null) {
            throw new ExaminationException(ErrorCode.VERIFICATION_CODE_NOT_EXIST);
        }
        if (!code.equals(values.get(0))) {
            if (Integer.parseInt((String) values.get(1)) == 1) {
                redisTemplate.delete(key);
            } else {
                redisTemplate.opsForHash().increment(key, "remain", -1);
            }
            throw new ExaminationException(ErrorCode.VERIFICATION_CODE_ERROR);
        }

        User user = userDomainService.findByPhone(phone);
        if (user == null) {
            user = new User();
            user.setName("用户" + phone);
            user.setGender("保密");
            user.setPhoto(storageService.getDefaultPhotoPath());
            user.setPhone(phone);
            user.setCreateTime(LocalDateTime.now());
            userDomainService.create(user);
        }
        return user;
    }

    @Override
    public void changePhoto(long userId, String path) {
        String oldPhoto = userDomainService.getPhoto(userId);
        User user = new User();
        user.setId(userId);
        user.setPhoto(path);
        userDomainService.update(user);

        eventMulticaster.multicastEvent(new FileChangedEvent(oldPhoto, path));
    }
}
