package com.xiaoyao.examination.goods.domain.enums;

public enum GoodsStatus {
    ON(1, "上架"),
    OFF(2, "下架");

    private final int status;
    private final String name;

    GoodsStatus(int status, String name) {
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
