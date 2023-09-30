package com.xiaoyao.examination.common.interfaces.goods.request;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateDiscountRequest implements Serializable {
    private long id;
    private @Nullable String name;
    private @Nullable String script;
    private @Nullable String description;
}
