package com.xiaoyao.examination.common.interfaces.goods.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class UpdateGoodsRequest implements Serializable {
    private long id;
    private String name;
    private String code;
    private String description;
    private BigDecimal originalPrice;
    private BigDecimal currentPrice;
    private Long discountId;
    private String image;
    private Integer type;
    private Integer sort;
    private String tag;
    private String departmentCheckup;
    private String laboratoryCheckup;
    private String medicalCheckup;
    private String otherCheckup;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDepartmentCheckup() {
        return departmentCheckup;
    }

    public void setDepartmentCheckup(String departmentCheckup) {
        this.departmentCheckup = departmentCheckup;
    }

    public String getLaboratoryCheckup() {
        return laboratoryCheckup;
    }

    public void setLaboratoryCheckup(String laboratoryCheckup) {
        this.laboratoryCheckup = laboratoryCheckup;
    }

    public String getMedicalCheckup() {
        return medicalCheckup;
    }

    public void setMedicalCheckup(String medicalCheckup) {
        this.medicalCheckup = medicalCheckup;
    }

    public String getOtherCheckup() {
        return otherCheckup;
    }

    public void setOtherCheckup(String otherCheckup) {
        this.otherCheckup = otherCheckup;
    }
}
