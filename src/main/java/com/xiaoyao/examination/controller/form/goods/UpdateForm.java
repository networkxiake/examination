package com.xiaoyao.examination.controller.form.goods;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class UpdateForm {
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

    private List<CreateForm.Item> departmentCheckup;

    private List<CreateForm.Item> laboratoryCheckup;

    private List<CreateForm.Item> medicalCheckup;

    private List<CreateForm.Item> otherCheckup;

    @Data
    public static class Item {
        private String name;
        private String description;
    }
}
