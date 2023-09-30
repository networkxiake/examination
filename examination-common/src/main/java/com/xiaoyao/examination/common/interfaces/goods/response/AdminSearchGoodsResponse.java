package com.xiaoyao.examination.common.interfaces.goods.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AdminSearchGoodsResponse implements Serializable {
    private long total;
    private List<Goods> goodsList;

    @Data
    public static class Goods implements Serializable {
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
