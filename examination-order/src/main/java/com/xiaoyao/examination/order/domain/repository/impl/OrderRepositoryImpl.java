package com.xiaoyao.examination.order.domain.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyao.examination.order.domain.entity.Order;
import com.xiaoyao.examination.order.domain.mapper.OrderMapper;
import com.xiaoyao.examination.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<Order> findOrderListForUserSummary(long userId) {
        return orderMapper.selectList(lambdaQuery(Order.class)
                .select(Order::getCount,
                        Order::getTotal)
                .eq(Order::getUserId, userId));
    }

    @Override
    public List<Order> findOrderListForSearch(long page, long size, String name, String code, Integer status, long[] total) {
        Page<Order> orderPage = orderMapper.selectPage(Page.of(page, size), lambdaQuery(Order.class)
                .select()
                .eq(StrUtil.isNotBlank(name), Order::getName, name)
                .eq(StrUtil.isNotBlank(code), Order::getCount, code)
                .eq(status != null, Order::getStatus, status));
        total[0] = orderPage.getTotal();
        return orderPage.getRecords();
    }

    @Override
    public Order findOrderForPayment(String paymentCode) {
        return orderMapper.selectOne(lambdaQuery(Order.class)
                .select(Order::getId,
                        Order::getUserId,
                        Order::getGoodsId,
                        Order::getCount)
                .eq(Order::getPaymentCode, paymentCode));
    }

    @Override
    public long getOrderIdByPaymentCode(String paymentCode) {
        return orderMapper.selectOne(lambdaQuery(Order.class)
                        .select(Order::getId)
                        .eq(Order::getPaymentCode, paymentCode))
                .getId();
    }

    @Override
    public Integer getStatus(long orderId) {
        Order order = orderMapper.selectOne(lambdaQuery(Order.class)
                .select(Order::getStatus)
                .eq(Order::getId, orderId));
        return order == null ? null : order.getStatus();
    }

    @Override
    public void save(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public boolean updateStatus(long userId, long orderId, Integer oldStatus, Integer newStatus) {
        return orderMapper.update(null, lambdaUpdate(Order.class)
                .set(Order::getStatus, newStatus)
                .eq(Order::getId, orderId)
                .eq(Order::getUserId, userId)
                .eq(Order::getStatus, oldStatus)) == 1;
    }

    @Override
    public void updateRefundDateAndRefundTime(long userId, long orderId, LocalDate refundDate, LocalDateTime refundTime) {
        orderMapper.update(null, lambdaUpdate(Order.class)
                .set(Order::getRefundDate, refundDate)
                .set(Order::getRefundTime, refundTime)
                .eq(Order::getId, orderId)
                .eq(Order::getUserId, userId));
    }

    @Override
    public String getPaymentCodeByOrderId(long orderId) {
        Order order = orderMapper.selectOne(lambdaQuery(Order.class)
                .select(Order::getPaymentCode)
                .eq(Order::getId, orderId));
        return order == null ? null : order.getPaymentCode();
    }
}
