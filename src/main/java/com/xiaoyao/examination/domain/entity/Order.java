package com.xiaoyao.examination.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName(value = "tb_order")
@Data
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
}