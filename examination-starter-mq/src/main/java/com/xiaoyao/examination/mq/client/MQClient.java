package com.xiaoyao.examination.mq.client;

import com.xiaoyao.examination.mq.message.OrderCreatedMessage;
import com.xiaoyao.examination.mq.message.OrderPayedMessage;

public interface MQClient {
    /**
     * 路由与支付相关的消息。
     */
    String PAY_EXCHANGE = "pay";

    /**
     * 存放关闭未付款订单的延时任务，消息过期后会被转发到 PAY_ORDER_CLOSED_QUEUE 队列中。
     */
    String PAY_ORDER_PAYING_QUEUE = "pay.order.paying";
    String PAY_ORDER_PAYING_ROUTE_KEY = "pay.order.paying";

    /**
     * 存放关闭未付款订单的消息，订单服务在监听到消息后需要尝试关闭未付款的订单。
     */
    String PAY_ORDER_CLOSED_QUEUE = "pay.order.closed";
    String PAY_ORDER_CLOSED_ROUTE_KEY = "pay.order.closed";

    /**
     * 存放订单已付款的消息，订单服务在监听到消息后需要处理支付的订单。
     */
    String PAY_ORDER_PAYED_QUEUE = "pay.order.payed";
    String PAY_ORDER_PAYED_ROUTE_KEY = "pay.order.payed";

    /**
     * 订单创建后发送定时关闭未支付订单的消息。
     *
     * @param message    消息对象
     * @param expiration 消息过期时间，单位为毫秒
     */
    void orderCreated(OrderCreatedMessage message, int expiration);

    /**
     * 订单已付款后发送消息。
     */
    void orderPayed(OrderPayedMessage message);
}
