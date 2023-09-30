package com.xiaoyao.examination.common.interfaces.goods.request;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateDiscountRequest implements Serializable {
    private String name;
    private String script;
    private @Nullable String description;
}
