package com.xiaoyao.examination.order.domain.repository;

import com.xiaoyao.examination.order.domain.entity.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findOrderListForUserSummary(long userId);

    Order findOrderForPayment(String paymentCode);

    long getOrderIdByPaymentCode(String paymentCode);

    void save(Order order);

    boolean updateStatus(long orderId, Integer oldStatus, Integer newStatus);
}
