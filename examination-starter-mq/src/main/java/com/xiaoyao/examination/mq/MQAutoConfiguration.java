package com.xiaoyao.examination.mq;

import com.xiaoyao.examination.mq.client.MQClient;
import com.xiaoyao.examination.mq.client.MQClientImpl;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MQAutoConfiguration {
    /**
     * 设置监听消息时的消息转换器。
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 设置发送和接收消息时的消息转换器。
     */
    @Bean
    public RabbitTemplateCustomizer rabbitTemplateCustomizer(MessageConverter messageConverter) {
        return rabbitTemplate -> rabbitTemplate.setMessageConverter(messageConverter);
    }

    @Bean
    public MQClient mqClient(AmqpAdmin admin, RabbitTemplate template) {
        declarePay(admin);
        return new MQClientImpl(template);
    }

    private void declarePay(AmqpAdmin admin) {
        admin.declareExchange(new TopicExchange(MQClient.PAY_EXCHANGE));

        admin.declareQueue(new Queue(MQClient.PAY_ORDER_PAYING_QUEUE, true, false, false, Map.of(
                "x-queue-type", "quorum",
                "x-dead-letter-exchange", MQClient.PAY_EXCHANGE,
                "x-dead-letter-routing-key", MQClient.PAY_ORDER_CLOSED_QUEUE)));
        admin.declareBinding(new Binding(MQClient.PAY_ORDER_PAYING_QUEUE, Binding.DestinationType.QUEUE,
                MQClient.PAY_EXCHANGE, MQClient.PAY_ORDER_PAYING_ROUTE_KEY, null));

        admin.declareQueue(new Queue(MQClient.PAY_ORDER_CLOSED_QUEUE, true, false, false, Map.of(
                "x-queue-type", "quorum")));
        admin.declareBinding(new Binding(MQClient.PAY_ORDER_CLOSED_QUEUE, Binding.DestinationType.QUEUE,
                MQClient.PAY_EXCHANGE, MQClient.PAY_ORDER_CLOSED_ROUTE_KEY, null));

        admin.declareQueue(new Queue(MQClient.PAY_ORDER_PAYED_QUEUE, true, false, false, Map.of(
                "x-queue-type", "quorum")));
        admin.declareBinding(new Binding(MQClient.PAY_ORDER_PAYED_QUEUE, Binding.DestinationType.QUEUE,
                MQClient.PAY_EXCHANGE, MQClient.PAY_ORDER_PAYED_ROUTE_KEY, null));
    }
}
