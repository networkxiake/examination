package com.xiaoyao.examination.common.interfaces.goods.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryGoodsResponse implements Serializable {
    private String name;
    private String code;
    private String description;
    private String originalPrice;
    private String currentPrice;
    private String discountId;
    private String image;
    private int type;
    private int sort;
    private List<String> tag;
    private List<Item> departmentCheckup;
    private List<Item> laboratoryCheckup;
    private List<Item> medicalCheckup;
    private List<Item> otherCheckup;

    @Data
    public static class Item implements Serializable {
        private String name;
        private String description;
    }
}
