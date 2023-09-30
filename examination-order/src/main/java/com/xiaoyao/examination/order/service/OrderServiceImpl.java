package com.xiaoyao.examination.order.service;

import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.common.interfaces.goods.DiscountService;
import com.xiaoyao.examination.common.interfaces.goods.GoodsService;
import com.xiaoyao.examination.common.interfaces.goods.response.SubmitOrderGoodsInfoResponse;
import com.xiaoyao.examination.common.interfaces.order.OrderService;
import com.xiaoyao.examination.common.interfaces.order.response.UserOrderSummaryResponse;
import com.xiaoyao.examination.common.interfaces.storage.StorageService;
import com.xiaoyao.examination.order.domain.entity.Order;
import com.xiaoyao.examination.order.domain.enums.OrderStatus;
import com.xiaoyao.examination.order.domain.service.OrderDomainService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@DubboService
public class OrderServiceImpl implements OrderService {
    private final OrderDomainService orderDomainService;

    @DubboReference
    private StorageService storageService;
    @DubboReference
    private DiscountService discountService;
    @DubboReference
    private GoodsService goodsService;

    public OrderServiceImpl(OrderDomainService orderDomainService) {
        this.orderDomainService = orderDomainService;
    }

    @Override
    public UserOrderSummaryResponse getUserOrderSummary(long userId) {
        List<Order> orders = orderDomainService.findAllOrderCountAndTotalByUserId(userId);
        int orderCount = 0;
        int goodsCount = 0;
        BigDecimal totalAmount = new BigDecimal("0.00");
        for (Order order : orders) {
            orderCount++;
            goodsCount += order.getCount();
            totalAmount = totalAmount.add(order.getTotal());
        }
        UserOrderSummaryResponse response = new UserOrderSummaryResponse();
        response.setOrderCount(orderCount);
        response.setGoodsCount(goodsCount);
        response.setTotalAmount(totalAmount.toString());
        return response;
    }

    @Override
    public String submitOrder(long userId, long goodsId, int count) {
        SubmitOrderGoodsInfoResponse goods = goodsService.getGoodsInfoInSubmitOrder(goodsId);
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
        order.setSnapshotMd5(goods.getSnapshotMd5());
        order.setStatus(OrderStatus.PAYING.getStatus());
        // TODO 生成支付单号
        order.setPaymentCode("123");
        orderDomainService.save(order);

        // TODO 创建付款单，返回支付地址
        return "123";
//        return payService.createPayOrder(order.getPaymentCode(),
//                order.getTotal().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN).intValue(),
//                "购买体检套餐", order.getId(), goodsId, count);
    }
}
