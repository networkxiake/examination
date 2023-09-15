package com.xiaoyao.examination.domain.enums;

import lombok.Getter;

@Getter
public enum GoodsStatus {
    ON(1, "上架"),
    OFF(2, "下架");

    private final int status;
    private final String name;

    GoodsStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }
}
