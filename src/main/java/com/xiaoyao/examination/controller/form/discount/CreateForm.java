package com.xiaoyao.examination.controller.form.discount;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateForm {
    @NotBlank
    private String name;

    @NotBlank
    private String script;

    private String description;
}
