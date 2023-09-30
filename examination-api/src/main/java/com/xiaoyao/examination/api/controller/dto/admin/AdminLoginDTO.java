package com.xiaoyao.examination.api.controller.dto.admin;

import lombok.Data;

@Data
public class AdminLoginDTO {
    private String token;
    private String name;
    private String photo;
}
