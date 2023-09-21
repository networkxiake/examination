package com.xiaoyao.examination.controller;

import com.xiaoyao.examination.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PayController {
    private final PayService payService;

    @PostMapping("/notify")
    public void notifyPay(HttpServletRequest request) {
        System.out.println("test");
        payService.payCallback(request);
    }
}
