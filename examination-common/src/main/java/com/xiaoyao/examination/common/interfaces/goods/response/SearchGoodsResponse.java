package com.xiaoyao.examination.common.interfaces.goods.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchGoodsResponse implements Serializable {
    List<Goods> goodsList;

    @Data
    public static class Goods implements Serializable {
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
