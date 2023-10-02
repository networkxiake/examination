package com.xiaoyao.examination.api.controller;

import com.xiaoyao.examination.common.interfaces.payment.PayService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @DubboReference
    private PayService payService;

    @PostMapping("/order/notify")
    public void orderNotify(HttpServletRequest request, @RequestBody String body) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        payService.payOrderCallback(headers, body);
    }
}
