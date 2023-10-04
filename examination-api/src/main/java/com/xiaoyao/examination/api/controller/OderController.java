package com.xiaoyao.examination.api.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.xiaoyao.examination.api.annotation.CheckLoginUser;
import com.xiaoyao.examination.api.controller.dto.order.SearchOrdersDTO;
import com.xiaoyao.examination.api.response.ResponseBody;
import com.xiaoyao.examination.api.response.ResponseBodyBuilder;
import com.xiaoyao.examination.api.util.UserStpUtil;
import com.xiaoyao.examination.common.interfaces.order.OrderService;
import com.xiaoyao.examination.common.interfaces.order.request.SearchOrdersRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
        return ResponseBodyBuilder.build(orderService.isPaid(UserStpUtil.getLoginId(), orderId));
    }

    @CheckLoginUser
    @PostMapping("/search")
    public ResponseBody<SearchOrdersDTO> search(@NotNull Integer page, @NotNull Integer size,
                                                String name, String code, Integer status) {
        SearchOrdersRequest request = new SearchOrdersRequest();
        request.setPage(page);
        request.setSize(size);
        request.setName(name);
        request.setCode(code);
        request.setStatus(status);
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(orderService.searchOrders(request), SearchOrdersDTO.class));
    }

    @CheckLoginUser
    @PostMapping("/refund")
    public ResponseBody<Void> refund(@NotNull Long orderId) {
        orderService.refund(UserStpUtil.getLoginId(), orderId);
        return ResponseBodyBuilder.build();
    }
}
