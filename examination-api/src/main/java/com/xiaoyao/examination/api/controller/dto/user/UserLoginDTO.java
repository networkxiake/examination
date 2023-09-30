package com.xiaoyao.examination.api.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginDTO {
    private String token;

    private String name;

    private String photo;
}
