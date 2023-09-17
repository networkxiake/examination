package com.xiaoyao.examination.controller.form.admin;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ChangePasswordForm {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$")
    private String oldPassword;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$")
    private String newPassword;
}
