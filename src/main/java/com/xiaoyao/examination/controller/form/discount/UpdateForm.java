package com.xiaoyao.examination.controller.form.discount;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateForm {
    @NotNull
    @Min(1)
    private long id;

    private String name;

    private String script;

    private String description;
}
