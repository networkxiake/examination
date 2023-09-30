package com.xiaoyao.examination.api.controller.form.discount;

import jakarta.validation.constraints.NotBlank;

public class CreateDiscountForm {
    @NotBlank
    private String name;

    @NotBlank
    private String script;

    private String description;

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
