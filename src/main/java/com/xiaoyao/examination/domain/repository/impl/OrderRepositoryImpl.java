package com.xiaoyao.examination.domain.repository.impl;

import com.xiaoyao.examination.domain.entity.Order;
import com.xiaoyao.examination.domain.mapper.OrderMapper;
import com.xiaoyao.examination.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderMapper orderMapper;

    @Override
    public List<Order> findAllOrderCountAndTotalByUserId(long userId) {
        return orderMapper.selectList(lambdaQuery(Order.class)
                .select(Order::getCount,
                        Order::getTotal)
                .eq(Order::getUserId, userId));
    }
}
