package com.xiaoyao.examination.controller.form.order;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SubmitOrderForm {
    @NotNull
    @Min(1)
    private long goodsId;

    @NotNull
    @Min(1)
    private int count;
}
