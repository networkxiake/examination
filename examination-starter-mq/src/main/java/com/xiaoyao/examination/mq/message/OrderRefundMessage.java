package com.xiaoyao.examination.mq.message;

public class OrderRefundMessage {
    private String paymentCode;

    public OrderRefundMessage(String paymentCodeByOrderId) {
        this.paymentCode = paymentCodeByOrderId;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }
}
