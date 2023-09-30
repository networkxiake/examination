package com.xiaoyao.examination.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName(value ="tb_user")
@Data
public class User {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 头像地址
     */
    private String photo;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}