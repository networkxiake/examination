package com.xiaoyao.examination.controller.form.discount;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SearchForm {
    @NotNull
    @Min(1)
    private long page;

    @NotNull
    @Min(1)
    private long size;

    private String name;
}
