package com.xiaoyao.examination.payment.service;

import cn.hutool.core.util.RandomUtil;
import com.xiaoyao.examination.common.interfaces.payment.PayService;
import com.xiaoyao.examination.common.interfaces.payment.request.CreatePayOrderRequest;
import com.xiaoyao.examination.common.interfaces.payment.response.CreatePayOrderResponse;
import com.xiaoyao.examination.mq.client.MQClient;
import com.xiaoyao.examination.mq.message.OrderPayedMessage;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Map;

@DubboService
public class PayServiceImpl implements PayService {
    private final MQClient mqClient;

    public PayServiceImpl(MQClient mqClient) {
        this.mqClient = mqClient;
    }

    @Override
    public CreatePayOrderResponse createPayOrder(CreatePayOrderRequest request) {
        String paymentCode = RandomUtil.randomString(32);

        // TODO 创建支付订单，根据不同的支付类型使用不同的支付成功回调。
        CreatePayOrderResponse response = new CreatePayOrderResponse();
        response.setPaymentCode(paymentCode);
        response.setPayUrl(paymentCode);
        return response;
    }

    @Override
    public void payOrderCallback(Map<String, String> headers, String body) {
        // TODO 将消息持久化到数据库，并添加事务以确保消息发送成功。
        mqClient.orderPayed(new OrderPayedMessage(body));
    }
}
