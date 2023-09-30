package com.xiaoyao.examination.api.controller.form.discount;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateDiscountForm {
    @NotNull
    @Min(1)
    private long id;

    private String name;

    private String script;

    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
