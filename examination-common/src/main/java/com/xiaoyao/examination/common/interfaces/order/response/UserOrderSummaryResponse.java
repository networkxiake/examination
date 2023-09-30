package com.xiaoyao.examination.common.interfaces.order.response;

import java.io.Serializable;

public class UserOrderSummaryResponse implements Serializable {
    private int orderCount;
    private String totalAmount;
    private int goodsCount;

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }
}
