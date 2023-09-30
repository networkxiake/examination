package com.xiaoyao.examination.api.controller.form.goods;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AdminSearchForm {
    @NotNull
    @Min(1)
    private long page;

    @NotNull
    @Min(1)
    private long size;

    private String name;

    private String code;

    @Min(1)
    private Integer type;

    @Min(1)
    private Integer status;

    @Min(1)
    private Integer sort;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
