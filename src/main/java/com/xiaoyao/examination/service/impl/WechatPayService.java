package com.xiaoyao.examination.service.impl;

import cn.felord.payment.wechat.v3.WechatApiProvider;
import cn.felord.payment.wechat.v3.model.Amount;
import cn.felord.payment.wechat.v3.model.PayParams;
import cn.hutool.core.util.RandomUtil;
import com.xiaoyao.examination.properties.ExaminationProperties;
import com.xiaoyao.examination.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WechatPayService implements PayService {
    private final String tenantId = "pc";

    private final WechatApiProvider wechatApiProvider;
    private final ExaminationProperties examinationProperties;

    @Override
    public String generateOrderId(int length) {
        return RandomUtil.randomString(length);
    }

    @Override
    public String createPayOrder(String orderId, int money, String description) {
        PayParams payParams = new PayParams();
        payParams.setOutTradeNo(orderId);
        Amount amount = new Amount();
        amount.setTotal(money);
        payParams.setAmount(amount);
        payParams.setDescription(description);
        payParams.setNotifyUrl(examinationProperties.getPayNotifyUrl());
        return wechatApiProvider.directPayApi(tenantId).nativePay(payParams).getBody().get("code_url").toString();
    }
}
