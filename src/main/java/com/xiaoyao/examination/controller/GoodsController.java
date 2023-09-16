package com.xiaoyao.examination.controller;

import com.xiaoyao.examination.annotation.CheckLoginAdmin;
import com.xiaoyao.examination.controller.dto.goods.GoodsTypeDTO;
import com.xiaoyao.examination.controller.dto.goods.QueryGoodsDTO;
import com.xiaoyao.examination.controller.dto.goods.SearchGoodsDTO;
import com.xiaoyao.examination.controller.form.goods.ChangeStatusForm;
import com.xiaoyao.examination.controller.form.goods.CreateForm;
import com.xiaoyao.examination.controller.form.goods.SearchForm;
import com.xiaoyao.examination.controller.form.goods.UpdateForm;
import com.xiaoyao.examination.response.ResponseBody;
import com.xiaoyao.examination.response.ResponseBodyBuilder;
import com.xiaoyao.examination.service.GoodsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {
    private final GoodsService goodsService;

    @CheckLoginAdmin
    @PostMapping("/search")
    public ResponseBody<SearchGoodsDTO> search(@Valid @RequestBody SearchForm form) {
        return ResponseBodyBuilder.build(goodsService.searchGoods(form));
    }

    @GetMapping("/type")
    public ResponseBody<GoodsTypeDTO> type() {
        return ResponseBodyBuilder.build(goodsService.getAllGoodsType());
    }

    @CheckLoginAdmin
    @PostMapping("/create")
    public ResponseBody<Void> create(@Valid @RequestBody CreateForm form) {
        goodsService.createGoods(form);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/change-status")
    public ResponseBody<Void> changeStatus(@Valid @RequestBody ChangeStatusForm form) {
        goodsService.changeStatus(form.getId(), form.getStatus());
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @GetMapping("/{id}")
    public ResponseBody<QueryGoodsDTO> query(@PathVariable("id") long id) {
        return ResponseBodyBuilder.build(goodsService.queryGoods(id));
    }

    @CheckLoginAdmin
    @PostMapping("/update")
    public ResponseBody<Void> update(@Valid @RequestBody UpdateForm form) {
        goodsService.updateGoods(form);
        return ResponseBodyBuilder.build();
    }
}
