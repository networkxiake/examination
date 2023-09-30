package com.xiaoyao.examination.order.domain.service.impl;

import com.xiaoyao.examination.order.domain.entity.Order;
import com.xiaoyao.examination.order.domain.repository.OrderRepository;
import com.xiaoyao.examination.order.domain.service.OrderDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDomainServiceImpl implements OrderDomainService {
    private final OrderRepository orderRepository;

    public OrderDomainServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAllOrderCountAndTotalByUserId(long userId) {
        return orderRepository.findAllOrderCountAndTotalByUserId(userId);
    }

    @Override
    public void save(Order order) {
        orderRepository.insert(order);
    }

    @Override
    public boolean payOrder(long orderId) {
        return orderRepository.payOrder(orderId);
    }
}
