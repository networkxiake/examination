package com.xiaoyao.examination.goods.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value ="tb_discount")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}