package com.xiaoyao.examination.payment.listener;

import com.xiaoyao.examination.common.interfaces.payment.PayService;
import com.xiaoyao.examination.mq.client.MQClient;
import com.xiaoyao.examination.mq.message.OrderCreatedMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPayListener {
    private final PayService payService;

    public OrderPayListener(PayService payService) {
        this.payService = payService;
    }

    @RabbitListener(queues = MQClient.PAY_ORDER_REFUND_QUEUE)
    public void listenOrderClosed(OrderCreatedMessage message) {
        payService.refund(message.getPaymentCode());
    }
}
