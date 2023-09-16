package com.xiaoyao.examination.controller.form.goods;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeFormItemForm {
    @NotNull
    @Min(1)
    private long id;

    @NotBlank
    private String path;
}
