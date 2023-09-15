package com.xiaoyao.examination.domain.enums;

import lombok.Getter;

@Getter
public enum GoodsType {
    UNLIMITED(1, "不限"),
    PARENT(2, "父母体检"),
    ENTRY(3, "入职体检"),
    CAREER(4, "职场白领"),
    INDIVIDUAL(5, "个人高端"),
    TEENAGER(6, "中青年体检");

    private final int type;
    private final String name;

    GoodsType(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
