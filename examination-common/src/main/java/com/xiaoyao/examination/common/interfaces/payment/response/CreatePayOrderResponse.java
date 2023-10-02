package com.xiaoyao.examination.common.interfaces.payment.response;

import java.io.Serializable;

public class CreatePayOrderResponse implements Serializable {
    private String paymentCode;
    private String payUrl;

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }
}
