package com.xiaoyao.examination.common.interfaces.user.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserProfileResponse implements Serializable {
    private String name;
    private String gender;
    private String photo;
    private String phone;
    private String createTime;
    private Integer goodsCount;
    private Integer orderCount;
    private String totalAmount;
}
