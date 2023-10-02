package com.xiaoyao.examination.payment.config;

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
        admin.declareQueue(new Queue("pay.order", true, false, false, Map.of(
                "x-queue-type", "quorum"
        )));
        admin.declareBinding(new Binding("pay.order", Binding.DestinationType.QUEUE, "pay",
                "pay.order", null));
    }
}
