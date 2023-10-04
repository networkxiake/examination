package com.xiaoyao.examination.common.interfaces.payment;

import com.xiaoyao.examination.common.interfaces.payment.request.CreatePayOrderRequest;
import com.xiaoyao.examination.common.interfaces.payment.response.CreatePayOrderResponse;

import java.util.Map;

public interface PayService {
    /**
     * 创建支付订单，根据不同的支付类型使用不同的支付成功回调。
     */
    CreatePayOrderResponse createPayOrder(CreatePayOrderRequest request);

    /**
     * 订单支付成功回调。
     */
    void payOrderCallback(Map<String, String> headers, String body);

    boolean isPaid(String paymentCode);

    void refund(String paymentCode);

    /**
     * 支付类型，在创建支付订单时需要根据支付类型来设置不同的通知回调地址。
     */
    enum PayType {
        /**
         * 订单
         */
        ORDER
    }
}
