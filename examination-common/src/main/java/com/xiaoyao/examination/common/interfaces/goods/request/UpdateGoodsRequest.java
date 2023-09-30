package com.xiaoyao.examination.common.interfaces.goods.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UpdateGoodsRequest implements Serializable {
    private long id;
    private String name;
    private String code;
    private String description;
    private BigDecimal originalPrice;
    private BigDecimal currentPrice;
    private Long discountId;
    private String image;
    private Integer type;
    private Integer sort;
    private String tag;
    private String departmentCheckup;
    private String laboratoryCheckup;
    private String medicalCheckup;
    private String otherCheckup;
}
