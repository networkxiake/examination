package com.xiaoyao.examination.controller.dto.goods;

import lombok.Data;

import java.util.List;

@Data
public class SearchGoodsDTO {
    List<Goods> goodsList;

    @Data
    public static class Goods {
        private String id;
        private String name;
        private String description;
        private String image;
        private String originalPrice;
        private String currentPrice;
        private int salesVolume;
        private String discountId;
    }
}
