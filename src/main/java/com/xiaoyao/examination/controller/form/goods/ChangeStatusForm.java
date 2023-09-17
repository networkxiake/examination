package com.xiaoyao.examination.controller.form.goods;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ChangeStatusForm {
    @NotNull
    @Min(1)
    private long id;

    @NotNull
    @Min(1)
    private int status;
}
