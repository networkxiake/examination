package com.xiaoyao.examination.mq.client;

import com.xiaoyao.examination.mq.message.OrderCreatedMessage;
import com.xiaoyao.examination.mq.message.OrderPayedMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MQClientImpl implements MQClient {
    private final RabbitTemplate rabbitTemplate;

    public MQClientImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private void send(String exchange, String routeKey, Object message) {
        send(exchange, routeKey, message, new MessageProperties());
    }

    private void send(String exchange, String routeKey, Object message, MessageProperties messageProperties) {
        if (message instanceof Message _message) {
            rabbitTemplate.send(exchange, routeKey, _message);
        } else {
            rabbitTemplate.send(exchange, routeKey, rabbitTemplate.getMessageConverter().toMessage(message, messageProperties));
        }
    }

    @Override
    public void orderCreated(OrderCreatedMessage message, int expiration) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration(String.valueOf(expiration));
        send(PAY_EXCHANGE, PAY_ORDER_PAYING_ROUTE_KEY, message, messageProperties);
    }

    @Override
    public void orderPayed(OrderPayedMessage message) {
        send(PAY_EXCHANGE, PAY_ORDER_PAYED_ROUTE_KEY, message);
    }
}
