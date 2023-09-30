package com.xiaoyao.examination.common.interfaces.goods.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchDiscountResponse implements Serializable {
    private long total;
    private List<Discount> discounts;

    @Data
    public static class Discount implements Serializable {
        private String id;
        private String name;
        private String description;
        private long goodsCount;

        public Discount(String id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
    }
}
