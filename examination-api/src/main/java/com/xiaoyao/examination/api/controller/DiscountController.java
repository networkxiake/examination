package com.xiaoyao.examination.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiaoyao.examination.api.annotation.CheckLoginAdmin;
import com.xiaoyao.examination.api.controller.dto.discount.ListDiscountDTO;
import com.xiaoyao.examination.api.controller.dto.discount.SearchDiscountDTO;
import com.xiaoyao.examination.api.controller.form.discount.CreateDiscountForm;
import com.xiaoyao.examination.api.controller.form.discount.UpdateDiscountForm;
import com.xiaoyao.examination.api.response.ResponseBody;
import com.xiaoyao.examination.api.response.ResponseBodyBuilder;
import com.xiaoyao.examination.common.interfaces.goods.DiscountService;
import com.xiaoyao.examination.common.interfaces.goods.request.CreateDiscountRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.SearchDiscountRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.UpdateDiscountRequest;
import com.xiaoyao.examination.common.interfaces.goods.response.SearchDiscountResponse;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discount")
public class DiscountController {
    @DubboReference
    private DiscountService discountService;

    @GetMapping("/list")
    public ResponseBody<ListDiscountDTO> list() {
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(discountService.list(), ListDiscountDTO.class));
    }

    @CheckLoginAdmin
    @GetMapping("/search")
    public ResponseBody<SearchDiscountDTO> search(long page, long size, String name) {
        SearchDiscountResponse response = discountService.search(new SearchDiscountRequest(page, size, name));
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(response, SearchDiscountDTO.class));
    }

    @CheckLoginAdmin
    @PostMapping("/create")
    public ResponseBody<Void> create(@Valid @RequestBody CreateDiscountForm form) {
        discountService.create(BeanUtil.copyProperties(form, CreateDiscountRequest.class));
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/update")
    public ResponseBody<Void> create(@Valid @RequestBody UpdateDiscountForm form) {
        discountService.update(BeanUtil.copyProperties(form, UpdateDiscountRequest.class));
        return ResponseBodyBuilder.build();
    }

    // TODO 新增删除折扣的接口
}
