package com.xiaoyao.examination.controller.dto.user;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String token;

    private String name;

    private String photo;
}
