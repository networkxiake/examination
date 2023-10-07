package com.xiaoyao.examination.order.domain.repository;

import com.xiaoyao.examination.order.domain.entity.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository {
    List<Order> findOrderListForUserSummary(long userId);

    List<Order> findOrderListForSearch(long page, long size, String name, String code, Integer status, long[] total);

    Order findOrderForPayment(String paymentCode);

    Order findOrderForRefund(long orderId);

    Long getOrderIdByPaymentCode(String paymentCode);

    Integer getStatus(long orderId);

    void save(Order order);

    boolean updateStatus(Long userId, long orderId, Integer oldStatus, Integer newStatus);

    void updateRefundDateAndRefundTime(long orderId, LocalDate refundDate, LocalDateTime refundTime);

    String getPaymentCodeByOrderId(long orderId);
}
