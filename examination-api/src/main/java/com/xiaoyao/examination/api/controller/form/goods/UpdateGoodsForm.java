package com.xiaoyao.examination.api.controller.form.goods;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class UpdateGoodsForm {
    @NotNull
    @Min(1)
    private long id;

    private String name;

    private String code;

    private String description;

    @Pattern(regexp = "^(\\d+)\\.(\\d{2})$")
    private String originalPrice;

    @Pattern(regexp = "^(\\d+)\\.(\\d{2})$")
    private String currentPrice;

    private Long discountId;

    private String image;

    @Min(1)
    private Integer type;

    private List<String> tag;

    @Min(1)
    private Integer sort;

    private List<Item> departmentCheckup;

    private List<Item> laboratoryCheckup;

    private List<Item> medicalCheckup;

    private List<Item> otherCheckup;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
