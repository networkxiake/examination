package com.xiaoyao.examination.api.controller.form.goods;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminSearchForm {
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

    @Min(1)
    private Integer sort;
}
