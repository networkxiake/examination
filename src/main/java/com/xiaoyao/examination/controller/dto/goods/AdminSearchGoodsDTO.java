package com.xiaoyao.examination.controller.dto.goods;

import lombok.Data;

import java.util.List;

@Data
public class AdminSearchGoodsDTO {
    private long total;
    private List<Goods> results;

    @Data
    public static class Goods {
        private String id;
        private String name;
        private String code;
        private String originalPrice;
        private String currentPrice;
        private String discount;
        private int salesVolume;
        private int type;
        private int status;
        private boolean hasExcel;
    }
}
