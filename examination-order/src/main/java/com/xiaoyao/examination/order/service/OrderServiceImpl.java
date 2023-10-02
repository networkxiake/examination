package com.xiaoyao.examination.order.service;

import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.common.interfaces.goods.DiscountService;
import com.xiaoyao.examination.common.interfaces.goods.GoodsService;
import com.xiaoyao.examination.common.interfaces.goods.response.SubmitOrderGoodsInfoResponse;
import com.xiaoyao.examination.common.interfaces.order.OrderService;
import com.xiaoyao.examination.common.interfaces.order.response.UserOrderSummaryResponse;
import com.xiaoyao.examination.common.interfaces.payment.PayService;
import com.xiaoyao.examination.common.interfaces.payment.request.CreatePayOrderRequest;
import com.xiaoyao.examination.common.interfaces.payment.response.CreatePayOrderResponse;
import com.xiaoyao.examination.order.domain.entity.Order;
import com.xiaoyao.examination.order.domain.enums.OrderStatus;
import com.xiaoyao.examination.order.domain.service.OrderDomainService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@DubboService
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderDomainService orderDomainService;
    private final RabbitTemplate rabbitTemplate;

    @DubboReference
    private PayService payService;
    @DubboReference
    private DiscountService discountService;
    @DubboReference
    private GoodsService goodsService;

    public OrderServiceImpl(OrderDomainService orderDomainService, RabbitTemplate rabbitTemplate) {
        this.orderDomainService = orderDomainService;
        this.rabbitTemplate = rabbitTemplate;
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

        Order order = new Order();
        order.setGoodsId(goodsId);
        order.setUserId(userId);
        order.setName(goods.getName());
        order.setDescription(goods.getDescription());
        order.setImage(goods.getImage());
        order.setUnitPrice(goods.getCurrentPrice());
        order.setCount(count);
        if (goods.getDiscountId() != null) {
            order.setTotal(discountService.compute(goods.getDiscountId(), goods.getCurrentPrice(), count));
        } else {
            order.setTotal(goods.getCurrentPrice().multiply(new BigDecimal(count)));
        }
        order.setSnapshotMd5(goods.getSnapshotMd5());
        order.setStatus(OrderStatus.PAY_WAITING.getStatus());
        CreatePayOrderResponse response = payService.createPayOrder(new CreatePayOrderRequest(
                order.getTotal().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN).intValue(),
                "购买体检套餐", PayService.PayType.ORDER));
        order.setPaymentCode(response.getPaymentCode());
        orderDomainService.save(order);

        // 发送定时消息到消息队列，如果超时未支付则关闭订单，超时时间为30分钟。
        rabbitTemplate.send("pay", "pay.order.paying",
                MessageBuilder.withBody(order.getPaymentCode().getBytes())
                        .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                        .setExpiration(String.valueOf(1000 * 60 * 30))
                        .build());

        return response.getPayUrl();
    }

    @RabbitListener(queues = "pay.order")
    public void payOrder(String paymentCode) throws Exception {
        if (!paySuccess(paymentCode)) {
            if (log.isWarnEnabled()) {
                log.warn("支付失败，paymentCode={}", paymentCode);
            }
            throw new Exception("支付失败");
        }
    }

    @RabbitListener(queues = "pay.order.close")
    public void closeOrder(String paymentCode) {
        long orderId = orderDomainService.getOrderIdByPaymentCode(paymentCode);
        orderDomainService.updateStatus(orderId, OrderStatus.PAY_WAITING.getStatus(), OrderStatus.CANCELED.getStatus());
    }

    @Override
    public boolean paySuccess(String paymentCode) {
        Order order = orderDomainService.findOrderByPaymentCode(paymentCode);
        // 使用CAS思想实现消息的幂等性
        if (orderDomainService.updateStatus(order.getId(), OrderStatus.PAY_WAITING.getStatus(), OrderStatus.SUBSCRIBE_WAITING.getStatus())) {
            goodsService.increaseSalesVolume(order.getGoodsId(), order.getCount());
            return true;
        }
        return false;
    }
}
