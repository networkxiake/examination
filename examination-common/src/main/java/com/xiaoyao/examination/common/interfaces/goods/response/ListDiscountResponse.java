package com.xiaoyao.examination.common.interfaces.goods.response;

import java.io.Serializable;
import java.util.List;

public class ListDiscountResponse implements Serializable {
    private List<Discount> discounts;

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public static class Discount implements Serializable {
        private String id;
        private String name;

        public Discount(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
