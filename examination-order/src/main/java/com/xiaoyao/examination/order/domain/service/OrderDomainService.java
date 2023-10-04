package com.xiaoyao.examination.order.domain.service;

import com.xiaoyao.examination.order.domain.entity.Order;

import java.util.List;

public interface OrderDomainService {
    List<Order> findAllOrderCountAndTotalByUserId(long userId);

    void save(Order order);

    Order findOrderByPaymentCode(String paymentCode);

    boolean updateStatus(long orderId, Integer oldStatus, Integer newStatus);

    long getOrderIdByPaymentCode(String paymentCode);

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
}
