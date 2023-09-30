package com.xiaoyao.examination.order.domain.repository;

import com.xiaoyao.examination.order.domain.entity.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findOrderListForUserSummary(long userId);

    void save(Order order);

    boolean updateStatus(long orderId, Integer oldStatus, Integer newStatus);
}
