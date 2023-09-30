package com.xiaoyao.examination.api.controller.form.discount;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDiscountForm {
    @NotBlank
    private String name;

    @NotBlank
    private String script;

    private String description;
}
