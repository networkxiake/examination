package com.xiaoyao.examination.order.domain.repository;

import com.xiaoyao.examination.order.domain.entity.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findAllOrderCountAndTotalByUserId(long userId);

    void insert(Order order);

    boolean payOrder(long orderId);
}
