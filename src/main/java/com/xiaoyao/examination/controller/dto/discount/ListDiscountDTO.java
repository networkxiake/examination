package com.xiaoyao.examination.controller.dto.discount;

import lombok.Data;

import java.util.List;

@Data
public class ListDiscountDTO {
    private List<Discount> discounts;

    @Data
    public static class Discount {
        private String id;
        private String name;

        public Discount(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
