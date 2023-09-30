package com.xiaoyao.examination.common.interfaces.goods.response;

import java.io.Serializable;
import java.util.List;

public class SearchDiscountResponse implements Serializable {
    private long total;
    private List<Discount> discounts;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(long goodsCount) {
            this.goodsCount = goodsCount;
        }
    }
}
