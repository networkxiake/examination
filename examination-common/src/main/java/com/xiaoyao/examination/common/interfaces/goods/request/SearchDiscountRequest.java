package com.xiaoyao.examination.common.interfaces.goods.request;

import jakarta.annotation.Nullable;

import java.io.Serializable;

public class SearchDiscountRequest implements Serializable {
    private long page;
    private long size;
    private @Nullable String name;

    public SearchDiscountRequest(long page, long size, @Nullable String name) {
        this.page = page;
        this.size = size;
        this.name = name;
    }

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
}
