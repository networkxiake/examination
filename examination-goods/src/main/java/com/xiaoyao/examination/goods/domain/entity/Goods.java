package com.xiaoyao.examination.goods.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName(value = "tb_goods")
public class Goods {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 套餐编号
     */
    private String code;

    /**
     * 套餐名称
     */
    private String name;

    /**
     * 套餐描述
     */
    private String description;

    /**
     * 套餐原价
     */
    private BigDecimal originalPrice;

    /**
     * 套餐现价
     */
    private BigDecimal currentPrice;

    /**
     * 折扣规则主键值
     */
    private Long discountId;

    /**
     * 封面图片
     */
    private String image;

    /**
     * 套餐类别
     */
    private Integer type;

    /**
     * 套餐分类
     */
    private Integer sort;

    /**
     * 套餐标签
     */
    private String tag;

    /**
     * 套餐销量
     */
    private Integer salesVolume;

    /**
     * 套餐状态
     */
    private Integer status;

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

    /**
     * 体检单项目
     */
    private String formItem;

    /**
     * 当前套餐的快照md5值
     */
    private String snapshotMd5;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getFormItem() {
        return formItem;
    }

    public void setFormItem(String formItem) {
        this.formItem = formItem;
    }

    public String getSnapshotMd5() {
        return snapshotMd5;
    }

    public void setSnapshotMd5(String snapshotMd5) {
        this.snapshotMd5 = snapshotMd5;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}