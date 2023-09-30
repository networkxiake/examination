package com.xiaoyao.examination.api.controller.dto.goods;

import lombok.Data;

import java.util.List;

@Data
public class AdminSearchGoodsDTO {
    private long total;
    private List<Goods> goodsList;

    @Data
    public static class Goods {
        private String id;
        private String name;
        private String code;
        private String originalPrice;
        private String currentPrice;
        private String discountId;
        private int salesVolume;
        private int type;
        private int status;
        private boolean hasExcel;
    }
}
