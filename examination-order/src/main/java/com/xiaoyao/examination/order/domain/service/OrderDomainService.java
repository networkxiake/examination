package com.xiaoyao.examination.order.domain.service;

import com.xiaoyao.examination.order.domain.entity.Order;

import java.util.List;

public interface OrderDomainService {
    List<Order> findAllOrderCountAndTotalByUserId(long userId);

    void save(Order order);

    Order findOrderByPaymentCode(String paymentCode);

    boolean updateStatus(long orderId, Integer oldStatus, Integer newStatus);

    long getOrderIdByPaymentCode(String paymentCode);
}
