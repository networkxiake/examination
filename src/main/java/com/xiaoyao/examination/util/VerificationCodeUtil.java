package com.xiaoyao.examination.util;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VerificationCodeUtil {
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
