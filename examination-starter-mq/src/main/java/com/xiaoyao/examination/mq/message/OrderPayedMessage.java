package com.xiaoyao.examination.mq.message;

public class OrderPayedMessage {
    private String paymentCode;

    public OrderPayedMessage() {
    }

    public OrderPayedMessage(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }
}
