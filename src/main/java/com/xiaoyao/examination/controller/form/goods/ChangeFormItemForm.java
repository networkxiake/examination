package com.xiaoyao.examination.controller.form.goods;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ChangeFormItemForm {
    @NotNull
    @Min(1)
    private long id;

    @NotBlank
    private String path;
}
