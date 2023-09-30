package com.xiaoyao.examination.api.controller.form.goods;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SearchGoodsForm {
    @NotNull
    @Min(0)
    private int pass;

    @NotNull
    @Min(1)
    private int size;

    private String name;

    @Min(1)
    private Integer type;

    @Pattern(regexp = "^(男性|女性)$")
    private String gender;

    @Pattern(regexp = "^(\\d+)\\.(\\d{2})$")
    private String bottomPrice;

    @Pattern(regexp = "^(\\d+)\\.(\\d{2})$")
    private String topPrice;

    @Pattern(regexp = "^(new|sale|priceAsc|priceDesc)$")
    private String order;
}