package com.xiaoyao.examination.controller.form.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePhotoForm {
    @NotBlank
    private String path;
}
