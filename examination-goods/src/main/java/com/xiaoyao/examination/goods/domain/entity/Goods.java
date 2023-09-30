package com.xiaoyao.examination.goods.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
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
}