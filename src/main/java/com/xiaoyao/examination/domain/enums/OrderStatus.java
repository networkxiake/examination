package com.xiaoyao.examination.domain.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PAYING(1, "待付款"),
    CANCELED(2, "已取消"),
    REFUNDED(3, "已退款"),
    SUBSCRIBING(4, "待预约"),
    FINISHED(5, "已完成");

    private final int status;
    private final String name;

    OrderStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }
}
