package com.xiaoyao.examination.common.interfaces.goods.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SearchDiscountRequest implements Serializable {
    private long page;
    private long size;
    private @Nullable String name;
}
