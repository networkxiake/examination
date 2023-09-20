package com.xiaoyao.examination.controller.form.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdateProfileForm {
    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^男$|^女$")
    private String gender;
}
