package com.xiaoyao.examination.order.domain.repository;

import com.xiaoyao.examination.order.domain.entity.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findOrderListForUserSummary(long userId);

    List<Order> findOrderListForSearch(long page, long size, String name, String code, Integer status, long[] total);

    Order findOrderForPayment(String paymentCode);

    long getOrderIdByPaymentCode(String paymentCode);

    Integer getStatus(long orderId);

    void save(Order order);

    boolean updateStatus(long orderId, Integer oldStatus, Integer newStatus);

    String getPaymentCodeByOrderId(long orderId);
}
