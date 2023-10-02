package com.xiaoyao.examination.order.domain.enums;

public enum OrderStatus {
    PAY_WAITING(1, "待支付"),
    CANCELED(2, "已取消"),
    REFUNDED(3, "已退款"),
    SUBSCRIBE_WAITING(4, "待预约"),
    FINISHED(5, "已完成");

    private final int status;
    private final String name;

    OrderStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
