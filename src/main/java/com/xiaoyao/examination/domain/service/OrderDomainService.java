package com.xiaoyao.examination.domain.service;

import com.xiaoyao.examination.domain.entity.Order;

import java.util.List;

public interface OrderDomainService {
    List<Order> findAllOrderCountAndTotalByUserId(long userId);

    void save(Order order);

    boolean payOrder(long orderId);
}
