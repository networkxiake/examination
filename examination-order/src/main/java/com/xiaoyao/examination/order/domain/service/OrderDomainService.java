package com.xiaoyao.examination.order.domain.service;

import com.xiaoyao.examination.order.domain.entity.Order;
import com.xiaoyao.examination.order.domain.enums.OrderStatus;

import java.util.List;

public interface OrderDomainService {
    List<Order> findAllOrderCountAndTotalByUserId(long userId);

    Order findOrderForRefund(long orderId);

    void save(Order order);

    Order findOrderByPaymentCode(String paymentCode);

    boolean updateStatus(Long userId, long orderId, OrderStatus oldStatus, OrderStatus newStatus);

    Long getOrderIdByPaymentCode(String paymentCode);

    String getPaymentCodeByOrderId(long orderId);

    /**
     * 获取订单的状态。
     */
    int getStatus(long orderId);

    /**
     * 判断指定的状态是否是已付款的状态。
     */
    boolean isPaidByStatus(int status);

    List<Order> searchOrders(long page, long size, String name, String code, Integer status, long[] total);

    void refund(long orderId);
}
