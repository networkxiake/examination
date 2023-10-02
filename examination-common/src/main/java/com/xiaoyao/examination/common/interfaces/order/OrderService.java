package com.xiaoyao.examination.common.interfaces.order;

import com.xiaoyao.examination.common.interfaces.order.response.UserOrderSummaryResponse;

public interface OrderService {
    /**
     * 查询用户所有订单的摘要信息。
     */
    UserOrderSummaryResponse getUserOrderSummary(long userId);

    /**
     * 提交订单。
     */
    String submitOrder(long userId, long goodsId, int count);

    /**
     * 支付成功后调用，返回值表示是否处理完支付。
     */
    boolean paySuccess(String paymentCode);
}
