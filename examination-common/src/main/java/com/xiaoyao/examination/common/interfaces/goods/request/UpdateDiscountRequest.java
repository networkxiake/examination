package com.xiaoyao.examination.common.interfaces.goods.request;

import jakarta.annotation.Nullable;

import java.io.Serializable;

public class UpdateDiscountRequest implements Serializable {
    private long id;
    private @Nullable String name;
    private @Nullable String script;
    private @Nullable String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getScript() {
        return script;
    }

    public void setScript(@Nullable String script) {
        this.script = script;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}
