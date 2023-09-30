package com.xiaoyao.examination.api.controller.form.goods;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UpdateGoodsForm {
    @NotNull
    @Min(1)
    private long id;

    private String name;

    private String code;

    private String description;

    @Pattern(regexp = "^(\\d+)\\.(\\d{2})$")
    private String originalPrice;

    @Pattern(regexp = "^(\\d+)\\.(\\d{2})$")
    private String currentPrice;

    private Long discountId;

    private String image;

    @Min(1)
    private Integer type;

    private List<String> tag;

    @Min(1)
    private Integer sort;

    private List<CreateGoodsForm.Item> departmentCheckup;

    private List<CreateGoodsForm.Item> laboratoryCheckup;

    private List<CreateGoodsForm.Item> medicalCheckup;

    private List<CreateGoodsForm.Item> otherCheckup;

    @Data
    public static class Item {
        private String name;
        private String description;
    }
}
