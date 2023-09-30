package com.xiaoyao.examination.common.interfaces.order;

import com.xiaoyao.examination.common.interfaces.order.response.UserOrderSummaryResponse;

public interface OrderService {
    /**
     * 查询用户所有订单的摘要信息。
     */
    UserOrderSummaryResponse getUserOrderSummary(long userId);

    String submitOrder(long userId, long goodsId, int count);
}
