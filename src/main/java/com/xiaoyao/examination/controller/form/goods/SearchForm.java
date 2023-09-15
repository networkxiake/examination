package com.xiaoyao.examination.controller.form.goods;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchForm {
    @NotNull
    @Min(1)
    private long page;

    @NotNull
    @Min(1)
    private long size;

    private String name;

    private String code;

    @Min(1)
    private Integer type;

    @Min(1)
    private Integer status;
}
