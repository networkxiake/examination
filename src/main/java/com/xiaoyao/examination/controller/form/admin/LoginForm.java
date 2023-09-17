package com.xiaoyao.examination.controller.form.admin;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class LoginForm {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$")
    private String password;
}
