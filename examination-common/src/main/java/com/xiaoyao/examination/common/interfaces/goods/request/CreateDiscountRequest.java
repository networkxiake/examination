package com.xiaoyao.examination.common.interfaces.goods.request;

import jakarta.annotation.Nullable;

import java.io.Serializable;

public class CreateDiscountRequest implements Serializable {
    private String name;
    private String script;
    private @Nullable String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
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
