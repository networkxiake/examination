package com.xiaoyao.examination.order.listener;

import com.rabbitmq.client.Channel;
import com.xiaoyao.examination.common.interfaces.order.OrderService;
import com.xiaoyao.examination.mq.client.MQClient;
import com.xiaoyao.examination.mq.message.OrderCreatedMessage;
import com.xiaoyao.examination.mq.message.OrderPayedMessage;
import com.xiaoyao.examination.order.domain.enums.OrderStatus;
import com.xiaoyao.examination.order.domain.service.OrderDomainService;
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

    private final OrderService orderService;
    private final OrderDomainService orderDomainService;

    public OrderPayListener(OrderService orderService, OrderDomainService orderDomainService) {
        this.orderService = orderService;
        this.orderDomainService = orderDomainService;
    }

    @RabbitListener(queues = MQClient.PAY_ORDER_PAYED_QUEUE)
    public void listenOrderPayed(OrderPayedMessage message, @Header(AmqpHeaders.DELIVERY_TAG) long tag, Channel channel) {
        try {
            orderService.paySuccess(message.getPaymentCode());
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("处理订单支付失败，paymentCode={} {}", message.getPaymentCode(), e.getMessage());
            try {
                channel.basicNack(tag, false, true);
            } catch (IOException ex) {
                // TODO 支付异常数据处理，将信息存入数据库，之后需要人工介入。
                log.warn("处理订单支付失败，paymentCode={} {}", message.getPaymentCode(), ex.getMessage());
            }
        }
    }

    @RabbitListener(queues = MQClient.PAY_ORDER_CLOSED_QUEUE)
    public void listenOrderClosed(OrderCreatedMessage message) {
        long orderId = orderDomainService.getOrderIdByPaymentCode(message.getPaymentCode());
        orderDomainService.updateStatus(orderId, OrderStatus.PAY_WAITING.getStatus(), OrderStatus.CANCELED.getStatus());
    }
}
