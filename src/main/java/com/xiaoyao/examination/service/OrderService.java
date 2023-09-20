package com.xiaoyao.examination.service;

import com.xiaoyao.examination.domain.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrderCountAndTotalByUserId(long userId);
}
