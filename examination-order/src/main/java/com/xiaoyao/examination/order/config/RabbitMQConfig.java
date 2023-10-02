package com.xiaoyao.examination.order.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitMQConfig implements InitializingBean {
    private final AmqpAdmin admin;

    public RabbitMQConfig(AmqpAdmin admin) {
        this.admin = admin;
    }

    @Override
    public void afterPropertiesSet() {
        admin.declareExchange(new TopicExchange("pay"));

        admin.declareQueue(new Queue("pay.order.paying", true, false, false, Map.of(
                "x-queue-type", "quorum",
                "x-dead-letter-exchange", "pay",
                "x-dead-letter-routing-key", "pay.order.close"
        )));
        admin.declareBinding(new Binding("pay.order.paying", Binding.DestinationType.QUEUE, "pay",
                "pay.order.paying", null));

        admin.declareQueue(new Queue("pay.order.close", true, false, false, Map.of(
                "x-queue-type", "quorum"
        )));
        admin.declareBinding(new Binding("pay.order.close", Binding.DestinationType.QUEUE, "pay",
                "pay.order.close", null));
    }
}
