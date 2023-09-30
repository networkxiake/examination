package com.xiaoyao.examination.api.controller.dto.goods;

import java.util.List;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public List<Item> getDepartmentCheckup() {
        return departmentCheckup;
    }

    public void setDepartmentCheckup(List<Item> departmentCheckup) {
        this.departmentCheckup = departmentCheckup;
    }

    public List<Item> getLaboratoryCheckup() {
        return laboratoryCheckup;
    }

    public void setLaboratoryCheckup(List<Item> laboratoryCheckup) {
        this.laboratoryCheckup = laboratoryCheckup;
    }

    public List<Item> getMedicalCheckup() {
        return medicalCheckup;
    }

    public void setMedicalCheckup(List<Item> medicalCheckup) {
        this.medicalCheckup = medicalCheckup;
    }

    public List<Item> getOtherCheckup() {
        return otherCheckup;
    }

    public void setOtherCheckup(List<Item> otherCheckup) {
        this.otherCheckup = otherCheckup;
    }

    public static class Item {
        private String name;
        private String description;

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
    }
}
