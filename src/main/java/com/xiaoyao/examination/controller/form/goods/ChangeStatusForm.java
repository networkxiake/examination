package com.xiaoyao.examination.controller.form.goods;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeStatusForm {
    @NotNull
    @Min(1)
    private long id;

    @NotNull
    @Min(1)
    private int status;
}
