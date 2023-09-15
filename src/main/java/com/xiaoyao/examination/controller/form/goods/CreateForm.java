package com.xiaoyao.examination.controller.form.goods;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class CreateForm {
    @NotBlank
    private String name;

    @NotBlank
    private String code;

    @NotBlank
    private String description;

    @NotBlank
    @Pattern(regexp = "^(\\d+)\\.(\\d{2})$")
    private String originalPrice;

    @NotBlank
    @Pattern(regexp = "^(\\d+)\\.(\\d{2})$")
    private String currentPrice;

    @Min(1)
    private Long discountId;

    @NotBlank
    private String image;

    @NotNull
    @Min(1)
    private Integer type;

    private List<String> tag;

    @Valid
    private List<Item> departmentCheckup;

    @Valid
    private List<Item> laboratoryCheckup;

    @Valid
    private List<Item> medicalCheckup;

    @Valid
    private List<Item> otherCheckup;

    @Data
    public static class Item {
        @NotBlank
        private String name;

        @NotBlank
        private String description;
    }
}
