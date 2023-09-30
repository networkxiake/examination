package com.xiaoyao.examination.common.interfaces.goods.request;

import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreateGoodsRequest implements Serializable {
    private String name;
    private String code;
    private String description;
    private BigDecimal originalPrice;
    private BigDecimal currentPrice;
    private @Nullable Long discountId;
    private String image;
    private int type;
    private int sort;
    private @Nullable String tag;
    private @Nullable String departmentCheckup;
    private @Nullable String laboratoryCheckup;
    private @Nullable String medicalCheckup;
    private @Nullable String otherCheckup;

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

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Nullable
    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(@Nullable Long discountId) {
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

    @Nullable
    public String getTag() {
        return tag;
    }

    public void setTag(@Nullable String tag) {
        this.tag = tag;
    }

    @Nullable
    public String getDepartmentCheckup() {
        return departmentCheckup;
    }

    public void setDepartmentCheckup(@Nullable String departmentCheckup) {
        this.departmentCheckup = departmentCheckup;
    }

    @Nullable
    public String getLaboratoryCheckup() {
        return laboratoryCheckup;
    }

    public void setLaboratoryCheckup(@Nullable String laboratoryCheckup) {
        this.laboratoryCheckup = laboratoryCheckup;
    }

    @Nullable
    public String getMedicalCheckup() {
        return medicalCheckup;
    }

    public void setMedicalCheckup(@Nullable String medicalCheckup) {
        this.medicalCheckup = medicalCheckup;
    }

    @Nullable
    public String getOtherCheckup() {
        return otherCheckup;
    }

    public void setOtherCheckup(@Nullable String otherCheckup) {
        this.otherCheckup = otherCheckup;
    }
}
