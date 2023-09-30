package com.xiaoyao.examination.api.controller.form.discount;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDiscountForm {
    @NotNull
    @Min(1)
    private long id;

    private String name;

    private String script;

    private String description;
}
