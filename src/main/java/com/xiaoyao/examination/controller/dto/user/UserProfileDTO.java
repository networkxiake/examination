package com.xiaoyao.examination.controller.dto.user;

import lombok.Data;

@Data
public class UserProfileDTO {
    private String name;
    private String gender;
    private String photo;
    private String phone;
    private String createTime;
    private Integer goodsCount;
    private Integer orderCount;
    private String totalAmount;
}
