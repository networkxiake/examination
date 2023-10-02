package com.xiaoyao.examination.payment.service;

import cn.hutool.core.util.RandomUtil;
import com.xiaoyao.examination.common.interfaces.payment.PayService;
import com.xiaoyao.examination.common.interfaces.payment.request.CreatePayOrderRequest;
import com.xiaoyao.examination.common.interfaces.payment.response.CreatePayOrderResponse;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Map;

@DubboService
public class PayServiceImpl implements PayService {
    private final RabbitTemplate rabbitTemplate;

    public PayServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
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
        rabbitTemplate.send("pay", "pay.order",
                MessageBuilder.withBody(body.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).build());
    }
}
