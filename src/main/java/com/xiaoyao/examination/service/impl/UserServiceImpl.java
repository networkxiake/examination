package com.xiaoyao.examination.service.impl;

import com.xiaoyao.examination.controller.dto.user.UserProfileDTO;
import com.xiaoyao.examination.domain.entity.Order;
import com.xiaoyao.examination.domain.entity.User;
import com.xiaoyao.examination.domain.service.UserDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.service.OrderService;
import com.xiaoyao.examination.service.StorageService;
import com.xiaoyao.examination.service.UserService;
import com.xiaoyao.examination.util.VerificationCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final OrderService orderService;

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
        checkCode(phone, code);

        User user = userDomainService.findByPhone(phone);
        if (user == null) {
            user = new User();
            user.setName("用户" + phone);
            user.setGender("男");
            user.setPhoto(storageService.getDefaultPhotoPath());
            user.setPhone(phone);
            user.setCreateTime(LocalDateTime.now());
            userDomainService.create(user);
        }
        return user;
    }

    private void checkCode(String phone, String code) {
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
    }

    @Override
    public String confirmPhoto(long userId, String path) {
        storageService.deleteUserPhoto(userDomainService.getPhoto(userId));

        User user = new User();
        user.setId(userId);
        user.setPhoto(path);
        userDomainService.update(user);
        storageService.confirmTempFile(path);
        return storageService.getPathDownloadingUrl(path);
    }

    @Override
    public UserProfileDTO profile(long userId) {
        User user = userDomainService.findById(userId);
        List<Order> orders = orderService.findAllOrderCountAndTotalByUserId(userId);

        UserProfileDTO dto = new UserProfileDTO();
        dto.setName(user.getName());
        dto.setGender(user.getGender());
        dto.setPhoto(storageService.getPathDownloadingUrl(user.getPhoto()));
        dto.setPhone(user.getPhone());
        dto.setCreateTime(user.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dto.setOrderCount(orders.size());
        int goodsCount = 0;
        BigDecimal totalAmount = new BigDecimal("0.00");
        for (Order order : orders) {
            goodsCount += order.getCount();
            totalAmount = totalAmount.add(order.getTotal());
        }
        dto.setGoodsCount(goodsCount);
        dto.setTotalAmount(totalAmount.toString());
        return dto;
    }

    @Override
    public void updateProfile(long userId, String name, String gender) {
        User user = new User();
        user.setId(userId);
        user.setName(name);
        user.setGender(gender);
        userDomainService.update(user);
    }

    @Override
    public void updatePhone(long loginId, String phone, String code) {
        checkCode(phone, code);

        User user = new User();
        user.setId(loginId);
        user.setPhone(phone);
        userDomainService.update(user);
    }
}
