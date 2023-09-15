package com.xiaoyao.examination.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName(value = "tb_goods", autoResultMap = true)
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
     * 套餐标签
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> tag;

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
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Item> departmentCheckup;

    /**
     * 实验室检查
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Item> laboratoryCheckup;

    /**
     * 医技检查
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Item> medicalCheckup;

    /**
     * 其它检查
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Item> otherCheckup;

    /**
     * 体检单项目
     */
    private Object formItem;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @Data
    public static class Item {
        /**
         * 检查项目名称
         */
        private String name;

        /**
         * 检查项目描述
         */
        private String description;
    }
}