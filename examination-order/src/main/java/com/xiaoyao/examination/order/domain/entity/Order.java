package com.xiaoyao.examination.order.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName(value = "tb_order")
public class Order {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 套餐主键值
     */
    private Long goodsId;

    /**
     * 用户主键值
     */
    private Long userId;

    /**
     * 套餐名称
     */
    private String name;

    /**
     * 套餐描述
     */
    private String description;

    /**
     * 套餐封面图片
     */
    private String image;

    /**
     * 套餐单价
     */
    private BigDecimal unitPrice;

    /**
     * 购买数量
     */
    private Integer count;

    /**
     * 订单总价
     */
    private BigDecimal total;

    /**
     * 创建日期
     */
    private LocalDate createDate;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 套餐快照主键值
     */
    private Long snapshotId;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 支付订单号
     */
    private String paymentCode;

    /**
     * 退款日期
     */
    private LocalDate refundDate;

    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(Long snapshotId) {
        this.snapshotId = snapshotId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public LocalDate getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDate refundDate) {
        this.refundDate = refundDate;
    }

    public LocalDateTime getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(LocalDateTime refundTime) {
        this.refundTime = refundTime;
    }
}