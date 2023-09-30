package com.xiaoyao.examination.user.util;

import cn.hutool.core.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class VerificationCodeUtil {
    private static final Logger log = LoggerFactory.getLogger(VerificationCodeUtil.class);

    public String generate() {
        return generate(6);
    }

    public String generate(int length) {
        return RandomUtil.randomNumbers(length);
    }

    @Async
    public void send(String phone, String code) {
        // TODO 发送验证码
        if (log.isInfoEnabled()) {
            log.info("发送验证码：{} -> {}", phone, code);
        }
    }
}
