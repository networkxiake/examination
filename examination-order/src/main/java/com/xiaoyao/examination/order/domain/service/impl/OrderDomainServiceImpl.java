package com.xiaoyao.examination.order.domain.service.impl;

import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.order.domain.entity.Order;
import com.xiaoyao.examination.order.domain.enums.OrderStatus;
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
    public Order findOrderForRefund(long orderId) {
        return orderRepository.findOrderForRefund(orderId);
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
    public boolean updateStatus(Long userId, long orderId, OrderStatus oldStatus, OrderStatus newStatus) {
        return orderRepository.updateStatus(userId, orderId, oldStatus.getStatus(), newStatus.getStatus());
    }

    @Override
    public Long getOrderIdByPaymentCode(String paymentCode) {
        return orderRepository.getOrderIdByPaymentCode(paymentCode);
    }

    @Override
    public String getPaymentCodeByOrderId(long orderId) {
        String paymentCode = orderRepository.getPaymentCodeByOrderId(orderId);
        if (paymentCode == null) {
            throw new ExaminationException(ErrorCode.ORDER_NOT_FOUND);
        }
        return paymentCode;
    }

    @Override
    public int getStatus(long orderId) {
        Integer status = orderRepository.getStatus(orderId);
        if (status == null) {
            throw new ExaminationException(ErrorCode.ORDER_NOT_FOUND);
        }
        return status;
    }

    @Override
    public boolean isPaidByStatus(int status) {
        return status == OrderStatus.SUBSCRIBE_WAITING.getStatus()
                || status == OrderStatus.FINISHED.getStatus();
    }

    @Override
    public List<Order> searchOrders(long page, long size, String name, String code, Integer status, long[] total) {
        return orderRepository.findOrderListForSearch(page, size, name, code, status, total);
    }

    @Override
    public void refund(long orderId) {
        orderRepository.updateRefundDateAndRefundTime(orderId, LocalDate.now(), LocalDateTime.now());
    }
}
