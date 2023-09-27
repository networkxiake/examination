package com.xiaoyao.examination.controller.dto.goods;

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
    private List<QueryGoodsDTO.Item> departmentCheckup;
    private List<QueryGoodsDTO.Item> laboratoryCheckup;
    private List<QueryGoodsDTO.Item> medicalCheckup;
    private List<QueryGoodsDTO.Item> otherCheckup;

    @Data
    public static class Item {
        private String name;
        private String description;
    }
}
