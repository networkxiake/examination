package com.xiaoyao.examination.api.controller;

import cn.hutool.core.util.IdUtil;
import com.xiaoyao.examination.api.annotation.CheckLoginUser;
import com.xiaoyao.examination.api.response.ResponseBody;
import com.xiaoyao.examination.api.response.ResponseBodyBuilder;
import com.xiaoyao.examination.api.util.UserStpUtil;
import com.xiaoyao.examination.common.interfaces.order.OrderService;
import jakarta.validation.constraints.Min;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/order")
public class OderController {
    @DubboReference
    private OrderService orderService;

    @CheckLoginUser
    @PostMapping("/submit")
    public ResponseBody<String> submitOrder(@Min(1) long goodsId, @Min(1) int count) {
        return ResponseBodyBuilder.build(orderService.submitOrder(UserStpUtil.getLoginId(), goodsId, count,
                IdUtil.getSnowflakeNextId()));
    }

    @CheckLoginUser
    @GetMapping("/pay-result/{orderId}")
    public ResponseBody<Boolean> payResult(@PathVariable long orderId) {
        return ResponseBodyBuilder.build(orderService.isPaid(orderId));
    }
}
