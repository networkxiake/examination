package com.xiaoyao.examination.common.interfaces.goods.request;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CreateGoodsRequest implements Serializable {
    private String name;
    private String code;
    private String description;
    private BigDecimal originalPrice;
    private BigDecimal currentPrice;
    private @Nullable Long discountId;
    private String image;
    private int type;
    private int sort;
    private @Nullable String tag;
    private @Nullable String departmentCheckup;
    private @Nullable String laboratoryCheckup;
    private @Nullable String medicalCheckup;
    private @Nullable String otherCheckup;
}
