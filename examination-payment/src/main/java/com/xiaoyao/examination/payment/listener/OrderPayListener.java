package com.xiaoyao.examination.payment.listener;

import com.rabbitmq.client.Channel;
import com.xiaoyao.examination.common.interfaces.payment.PayService;
import com.xiaoyao.examination.mq.client.MQClient;
import com.xiaoyao.examination.mq.message.OrderCreatedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderPayListener {
    private static final Logger log = LoggerFactory.getLogger(OrderPayListener.class);

    private final PayService payService;

    public OrderPayListener(PayService payService) {
        this.payService = payService;
    }

    @RabbitListener(queues = MQClient.PAY_ORDER_REFUND_QUEUE)
    public void listenOrderClosed(@Header(AmqpHeaders.DELIVERY_TAG) long tag, Channel channel,
                                  OrderCreatedMessage message) {
        try {
            payService.refund(message.getPaymentCode());
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.warn("处理订单退款失败，paymentCode={} {}", message.getPaymentCode(), e.getMessage());
            try {
                channel.basicNack(tag, false, true);
            } catch (IOException ex) {
                // TODO 支付异常数据处理，将信息存入数据库，之后需要人工介入。
                log.error("处理订单退款失败，paymentCode={} {}", message.getPaymentCode(), ex.getMessage());
            }
        }
    }
}
