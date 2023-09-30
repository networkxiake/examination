package com.xiaoyao.examination.common.interfaces.order.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserOrderSummaryResponse implements Serializable {
    private int orderCount;
    private String totalAmount;
    private int goodsCount;
}
