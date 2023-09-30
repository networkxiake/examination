package com.xiaoyao.examination.api.controller.dto.goods;

import lombok.Data;

import java.util.List;

@Data
public class QueryGoodsDTO {
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
    public static class Item {
        private String name;
        private String description;
    }
}
