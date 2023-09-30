package com.xiaoyao.examination.common.interfaces.goods.request;

import jakarta.annotation.Nullable;

import java.io.Serializable;

public class AdminSearchRequest implements Serializable {
    private long page;
    private long size;
    private @Nullable String name;
    private @Nullable String code;
    private @Nullable Integer type;
    private @Nullable Integer status;
    private @Nullable Integer sort;

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

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getCode() {
        return code;
    }

    public void setCode(@Nullable String code) {
        this.code = code;
    }

    @Nullable
    public Integer getType() {
        return type;
    }

    public void setType(@Nullable Integer type) {
        this.type = type;
    }

    @Nullable
    public Integer getStatus() {
        return status;
    }

    public void setStatus(@Nullable Integer status) {
        this.status = status;
    }

    @Nullable
    public Integer getSort() {
        return sort;
    }

    public void setSort(@Nullable Integer sort) {
        this.sort = sort;
    }
}
