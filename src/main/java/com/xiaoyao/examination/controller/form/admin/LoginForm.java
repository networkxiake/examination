package com.xiaoyao.examination.controller.form.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginForm {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$")
    private String password;
}
