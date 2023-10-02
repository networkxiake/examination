package com.xiaoyao.examination.goods.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

@TableName(value = "tb_goods_snapshot")
public class GoodsSnapshot {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 商品主键值
     */
    private Long goodsId;

    /**
     * 快照的md5值
     */
    private String snapshotMd5;

    /**
     * 套餐名称
     */
    private String name;

    /**
     * 套餐描述
     */
    private String description;

    /**
     * 封面图片
     */
    private String image;

    /**
     * 套餐原价
     */
    private BigDecimal originalPrice;

    /**
     * 套餐现价
     */
    private BigDecimal currentPrice;

    /**
     * 套餐类别
     */
    private Integer type;

    /**
     * 套餐标签
     */
    private String tag;

    /**
     * 科室检查
     */
    private String departmentCheckup;

    /**
     * 实验室检查
     */
    private String laboratoryCheckup;

    /**
     * 医技检查
     */
    private String medicalCheckup;

    /**
     * 其它检查
     */
    private String otherCheckup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getSnapshotMd5() {
        return snapshotMd5;
    }

    public void setSnapshotMd5(String snapshotMd5) {
        this.snapshotMd5 = snapshotMd5;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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