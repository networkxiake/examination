package com.xiaoyao.examination.common.interfaces.payment.request;

import com.xiaoyao.examination.common.interfaces.payment.PayService;

import java.io.Serializable;

public class CreatePayOrderRequest implements Serializable {
    private int money;
    private String description;
    private PayService.PayType payType;

    public CreatePayOrderRequest() {
    }

    public CreatePayOrderRequest(int money, String description, PayService.PayType payType) {
        this.money = money;
        this.description = description;
        this.payType = payType;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PayService.PayType getPayType() {
        return payType;
    }

    public void setPayType(PayService.PayType payType) {
        this.payType = payType;
    }
}
