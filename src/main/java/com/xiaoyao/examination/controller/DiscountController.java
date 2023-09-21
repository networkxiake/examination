package com.xiaoyao.examination.controller;

import com.xiaoyao.examination.annotation.CheckLoginAdmin;
import com.xiaoyao.examination.controller.dto.discount.ListDiscountDTO;
import com.xiaoyao.examination.controller.dto.discount.SearchDiscountDTO;
import com.xiaoyao.examination.controller.form.discount.CreateForm;
import com.xiaoyao.examination.controller.form.discount.SearchForm;
import com.xiaoyao.examination.controller.form.discount.UpdateForm;
import com.xiaoyao.examination.response.ResponseBody;
import com.xiaoyao.examination.response.ResponseBodyBuilder;
import com.xiaoyao.examination.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discount")
public class DiscountController {
    private final DiscountService discountService;

    @GetMapping("/list")
    public ResponseBody<ListDiscountDTO> list() {
        return ResponseBodyBuilder.build(discountService.list());
    }

    @CheckLoginAdmin
    @PostMapping("/search")
    public ResponseBody<SearchDiscountDTO> search(@Valid @RequestBody SearchForm form) {
        return ResponseBodyBuilder.build(discountService.search(form));
    }

    @CheckLoginAdmin
    @PostMapping("/create")
    public ResponseBody<Void> create(@Valid @RequestBody CreateForm form) {
        discountService.create(form);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/update")
    public ResponseBody<Void> create(@Valid @RequestBody UpdateForm form) {
        discountService.update(form);
        return ResponseBodyBuilder.build();
    }
}
