package com.xiaoyao.examination.common.interfaces.goods.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ListDiscountResponse implements Serializable {
    private List<Discount> discounts;

    @Data
    public static class Discount implements Serializable {
        private String id;
        private String name;

        public Discount(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
