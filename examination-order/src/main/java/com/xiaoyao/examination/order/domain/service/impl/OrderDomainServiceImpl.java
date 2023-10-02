package com.xiaoyao.examination.order.domain.service.impl;

import com.xiaoyao.examination.order.domain.entity.Order;
import com.xiaoyao.examination.order.domain.repository.OrderRepository;
import com.xiaoyao.examination.order.domain.service.OrderDomainService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderDomainServiceImpl implements OrderDomainService {
    private final OrderRepository orderRepository;

    public OrderDomainServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAllOrderCountAndTotalByUserId(long userId) {
        return orderRepository.findOrderListForUserSummary(userId);
    }

    @Override
    public void save(Order order) {
        order.setCreateDate(LocalDate.now());
        order.setCreateTime(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    public Order findOrderByPaymentCode(String paymentCode) {
        return orderRepository.findOrderForPayment(paymentCode);
    }

    @Override
    public boolean updateStatus(long orderId, Integer oldStatus, Integer newStatus) {
        return orderRepository.updateStatus(orderId, oldStatus, newStatus);
    }

    @Override
    public long getOrderIdByPaymentCode(String paymentCode) {
        return orderRepository.getOrderIdByPaymentCode(paymentCode);
    }
}
