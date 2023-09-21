package com.xiaoyao.examination.controller.dto.discount;

import lombok.Data;

import java.util.List;

@Data
public class SearchDiscountDTO {
    private long total;
    private List<Discount> discounts;

    @Data
    public static class Discount {
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
