package com.xiaoyao.examination.api.controller.form.goods;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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


    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(String bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    public String getTopPrice() {
        return topPrice;
    }

    public void setTopPrice(String topPrice) {
        this.topPrice = topPrice;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}