package com.xiaoyao.examination.service.impl;

import com.xiaoyao.examination.domain.entity.Goods;
import com.xiaoyao.examination.domain.entity.Order;
import com.xiaoyao.examination.domain.enums.OrderStatus;
import com.xiaoyao.examination.domain.service.GoodsDomainService;
import com.xiaoyao.examination.domain.service.GoodsSnapshotDomainService;
import com.xiaoyao.examination.domain.service.OrderDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.service.DiscountService;
import com.xiaoyao.examination.service.OrderService;
import com.xiaoyao.examination.service.PayService;
import com.xiaoyao.examination.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDomainService orderDomainService;
    private final GoodsDomainService goodsDomainService;
    private final GoodsSnapshotDomainService goodsSnapshotDomainService;
    private final StorageService storageService;
    private final DiscountService discountService;

    @Resource
    private PayService payService;


    @Override
    public List<Order> findAllOrderCountAndTotalByUserId(long userId) {
        return orderDomainService.findAllOrderCountAndTotalByUserId(userId);
    }

    @Override
    public String submitOrder(long userId, long goodsId, int count) {
        Goods goods = goodsDomainService.getOrderGoodsById(goodsId);
        if (goods == null) {
            throw new ExaminationException(ErrorCode.GOODS_NOT_FOUND);
        }

        // 创建订单
        Order order = new Order();
        order.setGoodsId(goodsId);
        order.setUserId(userId);
        order.setName(goods.getName());
        order.setDescription(goods.getDescription());
        order.setImage(storageService.getPathDownloadingUrl(goods.getImage()));
        order.setUnitPrice(goods.getCurrentPrice());
        order.setCount(count);
        if (goods.getDiscountId() != null) {
            order.setTotal(discountService.compute(goods.getDiscountId(), goods.getCurrentPrice(), count));
        } else {
            order.setTotal(goods.getCurrentPrice().multiply(new BigDecimal(count)));
        }
        order.setCreateTime(LocalDateTime.now());
        order.setCreateDate(LocalDate.now());
        order.setSnapshotId(goodsSnapshotDomainService.queryNewestSnapshotId(goodsId, goods.getUpdateTime()));
        order.setStatus(OrderStatus.PAYING.getStatus());
        order.setPaymentCode(payService.generateOrderId());
        orderDomainService.save(order);

        // 创建付款单
        return payService.createPayOrder(order.getPaymentCode(),
                order.getTotal().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN).intValue(),
                "购买体检套餐", order.getId(), goodsId, count);
    }

    @Override
    public void payOrder(long orderId, long goodsId, int count) {
        boolean paid = orderDomainService.payOrder(orderId);
        if (!paid) {
            throw new ExaminationException(ErrorCode.ORDER_STATUS_ERROR);
        }

        // 支付成功，增加套餐销量。
        goodsDomainService.increaseSales(goodsId, count);
    }
}
