package com.xiaoyao.examination.controller.dto.goods;

import lombok.Data;

import java.util.List;

@Data
public class SearchGoodsDTO {
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
        private String type;
        private int status;
    }
}
