package com.xiaoyao.examination.controller;

import com.xiaoyao.examination.annotation.CheckLoginUser;
import com.xiaoyao.examination.controller.form.order.SubmitOrderForm;
import com.xiaoyao.examination.response.ResponseBody;
import com.xiaoyao.examination.response.ResponseBodyBuilder;
import com.xiaoyao.examination.service.OrderService;
import com.xiaoyao.examination.util.UserStpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OderController {
    private final OrderService orderService;

    @CheckLoginUser
    @PostMapping("/submit")
    public ResponseBody<String> submitOrder(@Valid @RequestBody SubmitOrderForm form) {
        return ResponseBodyBuilder.build(orderService.submitOrder(UserStpUtil.getLoginId(), form.getGoodsId(), form.getCount()));
    }
}
