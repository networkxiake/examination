package com.xiaoyao.examination.controller.form.admin;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePhotoForm {
    @NotBlank
    private String path;
}
