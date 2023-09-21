package com.xiaoyao.examination.domain.repository;

import com.xiaoyao.examination.domain.entity.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findAllOrderCountAndTotalByUserId(long userId);

    void insert(Order order);

    boolean payOrder(long orderId);
}
