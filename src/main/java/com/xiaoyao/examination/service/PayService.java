package com.xiaoyao.examination.service;

public interface PayService {
    /**
     * 随机生成长度为32位的订单号。
     */
    default String generateOrderId() {
        return generateOrderId(32);
    }

    /**
     * 随机生成指定长度的订单号。
     */
    String generateOrderId(int length);

    /**
     * 创建支付订单。
     *
     * @param orderId     订单号
     * @param money       金额，单位为分
     * @param description 描述
     * @return 用于生成支付二维码的支付链接
     */
    String createPayOrder(String orderId, int money, String description);
}
