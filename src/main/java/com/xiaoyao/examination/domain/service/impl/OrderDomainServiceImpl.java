package com.xiaoyao.examination.domain.service.impl;

import com.xiaoyao.examination.domain.entity.Order;
import com.xiaoyao.examination.domain.repository.OrderRepository;
import com.xiaoyao.examination.domain.service.OrderDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDomainServiceImpl implements OrderDomainService {
    private final OrderRepository orderRepository;

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
