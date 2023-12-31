package com.xiaoyao.examination.goods.domain.enums;

public enum GoodsSort {
    ACTIVITY(1, "活动专区"),
    HOT_SALE(2, "热卖套餐"),
    NEW(3, "新品推荐");

    private final int sort;
    private final String name;

    GoodsSort(int sort, String name) {
        this.sort = sort;
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public String getName() {
        return name;
    }
}
