package com.xiaoyao.examination.order.domain.repository.impl;

import com.xiaoyao.examination.order.domain.entity.Order;
import com.xiaoyao.examination.order.domain.enums.OrderStatus;
import com.xiaoyao.examination.order.domain.mapper.OrderMapper;
import com.xiaoyao.examination.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;
import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaUpdate;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderMapper orderMapper;

    public OrderRepositoryImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public List<Order> findAllOrderCountAndTotalByUserId(long userId) {
        return orderMapper.selectList(lambdaQuery(Order.class)
                .select(Order::getCount,
                        Order::getTotal)
                .eq(Order::getUserId, userId));
    }

    @Override
    public void insert(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public boolean payOrder(long orderId) {
        return orderMapper.update(null, lambdaUpdate(Order.class)
                .set(Order::getStatus, OrderStatus.SUBSCRIBING)
                .eq(Order::getId, orderId)
                .eq(Order::getStatus, OrderStatus.PAYING)) == 1;
    }
}
