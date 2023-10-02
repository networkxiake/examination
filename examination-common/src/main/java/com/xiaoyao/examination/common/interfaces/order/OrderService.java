package com.xiaoyao.examination.common.interfaces.order;

import com.xiaoyao.examination.common.interfaces.order.response.UserOrderSummaryResponse;

public interface OrderService {
    /**
     * 查询用户所有订单的摘要信息。
     */
    UserOrderSummaryResponse getUserOrderSummary(long userId);

    /**
     * 提交订单，并通过全局唯一id来确保下单的幂等性。
     *
     * @return 返回订单的支付链接，如果订单已经创建了（幂等），则返回null。
     */
    String submitOrder(long userId, long goodsId, int count, long globalId);

    /**
     * 支付成功后调用，返回值表示是否处理完支付。
     */
    void paySuccess(String paymentCode);
}
