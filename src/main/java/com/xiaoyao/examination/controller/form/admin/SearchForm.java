package com.xiaoyao.examination.controller.form.admin;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class SearchForm {
    @Min(1)
    private long page;

    @Min(1)
    private long size;

    private String name;
}
