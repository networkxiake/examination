package com.xiaoyao.examination.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value ="tb_discount")
@Data
public class Discount {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 折扣名称
     */
    private String name;

    /**
     * 折扣脚本
     */
    private String script;

    /**
     * 折扣描述
     */
    private String description;
}