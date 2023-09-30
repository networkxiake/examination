package com.xiaoyao.examination.goods.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName(value = "tb_goods_snapshot")
@Data
public class GoodsSnapshot {
    /**
     * 主键
     */
    @TableId
    private Long id;

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
}