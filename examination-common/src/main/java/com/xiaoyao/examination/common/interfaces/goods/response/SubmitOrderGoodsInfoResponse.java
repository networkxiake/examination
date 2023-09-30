package com.xiaoyao.examination.common.interfaces.goods.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class SubmitOrderGoodsInfoResponse implements Serializable {
    private String name;
    private String description;
    private String image;
    private BigDecimal currentPrice;
    private Long discountId;
    private String snapshotMd5;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public String getSnapshotMd5() {
        return snapshotMd5;
    }

    public void setSnapshotMd5(String snapshotMd5) {
        this.snapshotMd5 = snapshotMd5;
    }
}
