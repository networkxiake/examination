package com.xiaoyao.examination.controller;

import com.xiaoyao.examination.controller.dto.discount.ListDiscountDTO;
import com.xiaoyao.examination.response.ResponseBody;
import com.xiaoyao.examination.response.ResponseBodyBuilder;
import com.xiaoyao.examination.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discount")
public class DiscountController {
    private final DiscountService discountService;

    @GetMapping("/list")
    public ResponseBody<ListDiscountDTO> list() {
        return ResponseBodyBuilder.build(discountService.list());
    }
}
