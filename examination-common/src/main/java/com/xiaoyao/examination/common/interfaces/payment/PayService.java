package com.xiaoyao.examination.common.interfaces.payment;

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
     * @param paymentId   支付单号
     * @param money       金额，单位为分
     * @param description 描述
     * @param orderId     订单id
     * @param goodsId     套餐id
     * @param count       购买数量
     * @return 用于生成支付二维码的支付链接
     */
    String createPayOrder(String paymentId, int money, String description,
                          long orderId, long goodsId, int count);
}
