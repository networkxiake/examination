package com.xiaoyao.examination.controller.form.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangePasswordForm {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$")
    private String oldPassword;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$")
    private String newPassword;
}
