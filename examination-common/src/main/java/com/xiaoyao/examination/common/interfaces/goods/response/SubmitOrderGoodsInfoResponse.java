package com.xiaoyao.examination.common.interfaces.goods.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SubmitOrderGoodsInfoResponse implements Serializable {
    private String name;
    private String description;
    private String image;
    private BigDecimal currentPrice;
    private Long discountId;
    private String snapshotMd5;
}
