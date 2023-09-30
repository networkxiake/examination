package com.xiaoyao.examination.common.interfaces.goods.request;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdminSearchRequest implements Serializable {
    private long page;
    private long size;
    private @Nullable String name;
    private @Nullable String code;
    private @Nullable Integer type;
    private @Nullable Integer status;
    private @Nullable Integer sort;
}
