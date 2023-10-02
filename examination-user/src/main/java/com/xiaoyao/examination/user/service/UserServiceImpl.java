package com.xiaoyao.examination.user.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.common.interfaces.order.OrderService;
import com.xiaoyao.examination.common.interfaces.order.response.UserOrderSummaryResponse;
import com.xiaoyao.examination.common.interfaces.storage.StorageService;
import com.xiaoyao.examination.common.interfaces.user.UserService;
import com.xiaoyao.examination.common.interfaces.user.response.ApplyUploadPhotoResponse;
import com.xiaoyao.examination.common.interfaces.user.response.UserLoginResponse;
import com.xiaoyao.examination.common.interfaces.user.response.UserProfileResponse;
import com.xiaoyao.examination.user.domain.entity.User;
import com.xiaoyao.examination.user.domain.service.UserDomainService;
import com.xiaoyao.examination.user.util.VerificationCodeUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@DubboService
public class UserServiceImpl implements UserService {
    private final String IMAGE_CODE_PREFIX = "image-code:";
    private final String VERIFICATION_CODE_PREFIX = "verification-code:";
    private final String VERIFICATION_CODE_IP_PREFIX = "verification-code-ip:";

    private final UserDomainService userDomainService;
    private final StringRedisTemplate redisTemplate;
    private final VerificationCodeUtil verificationCodeUtil;

    @DubboReference
    private StorageService storageService;
    @DubboReference
    private OrderService orderService;

    public UserServiceImpl(UserDomainService userDomainService, StringRedisTemplate redisTemplate,
                           VerificationCodeUtil verificationCodeUtil) {
        this.userDomainService = userDomainService;
        this.redisTemplate = redisTemplate;
        this.verificationCodeUtil = verificationCodeUtil;
    }

    @Override
    public String generateImageCode(long userId, Integer width, Integer height) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width != null ? width : 200, height != null ? height : 100);
        lineCaptcha.createCode();
        redisTemplate.opsForValue().set(IMAGE_CODE_PREFIX + userId, lineCaptcha.getCode(), 1, TimeUnit.MINUTES);
        return lineCaptcha.getImageBase64();
    }

    @Override
    public void sendVerificationCode(String ip, long userId, String phone) {
        // 验证图形验证码
        String imageCode = redisTemplate.opsForValue().get(IMAGE_CODE_PREFIX + userId);
        if (imageCode == null) {
            throw new ExaminationException(ErrorCode.IMAGE_CODE_NOT_EXIST);
        } else if (!imageCode.equals(phone)) {
            throw new ExaminationException(ErrorCode.IMAGE_CODE_ERROR);
        }

        // 防止频繁发送验证码
        if (Boolean.TRUE.equals(redisTemplate.hasKey(VERIFICATION_CODE_IP_PREFIX + ip))) {
            throw new ExaminationException(ErrorCode.VERIFICATION_CODE_SEND_TOO_FREQUENTLY);
        }
        redisTemplate.opsForValue().set(VERIFICATION_CODE_IP_PREFIX + ip, "1", 1, TimeUnit.MINUTES);
        String code = verificationCodeUtil.generate();
        redisTemplate.opsForHash().putAll(VERIFICATION_CODE_PREFIX + phone, Map.of(
                "code", code,
                "remain", "5"
        ));
        redisTemplate.expire(VERIFICATION_CODE_PREFIX + phone, 5, TimeUnit.MINUTES);

        verificationCodeUtil.send(phone, code);
    }

    @Override
    public UserLoginResponse login(String phone, String code) {
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
        return new UserLoginResponse(user.getId(), user.getName(), storageService.getPathDownloadingUrl(user.getPhoto()));
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
        redisTemplate.delete(key);
    }

    @Override
    public ApplyUploadPhotoResponse applyUploadPhoto(String suffix) {
        List<String> strings = storageService.applySpaceForPhoto(IdUtil.fastSimpleUUID() + "." + suffix);
        return new ApplyUploadPhotoResponse(strings.get(0), strings.get(1));
    }

    @Override
    public String confirmPhoto(long userId, String path) {
        storageService.changeReference(userDomainService.getPhoto(userId), path);

        User user = new User();
        user.setId(userId);
        user.setPhoto(path);
        userDomainService.update(user);

        return storageService.getPathDownloadingUrl(path);
    }

    @Override
    public UserProfileResponse profile(long userId) {
        User user = userDomainService.findById(userId);
        UserOrderSummaryResponse summary = orderService.getUserOrderSummary(userId);

        UserProfileResponse response = new UserProfileResponse();
        response.setName(user.getName());
        response.setGender(user.getGender());
        response.setPhoto(storageService.getPathDownloadingUrl(user.getPhoto()));
        response.setPhone(user.getPhone());
        response.setCreateTime(user.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        response.setOrderCount(summary.getOrderCount());
        response.setGoodsCount(summary.getGoodsCount());
        response.setTotalAmount(summary.getTotalAmount());
        return response;
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
