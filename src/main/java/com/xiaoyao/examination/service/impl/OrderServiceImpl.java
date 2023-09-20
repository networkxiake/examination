package com.xiaoyao.examination.service.impl;

import com.xiaoyao.examination.domain.entity.Order;
import com.xiaoyao.examination.domain.service.OrderDomainService;
import com.xiaoyao.examination.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDomainService orderDomainService;


    @Override
    public List<Order> findAllOrderCountAndTotalByUserId(long userId) {
        return orderDomainService.findAllOrderCountAndTotalByUserId(userId);
    }
}
