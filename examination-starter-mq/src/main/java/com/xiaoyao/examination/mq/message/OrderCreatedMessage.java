package com.xiaoyao.examination.mq.message;

public class OrderCreatedMessage {
    private String paymentCode;

    public OrderCreatedMessage() {
    }

    public OrderCreatedMessage(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }
}
