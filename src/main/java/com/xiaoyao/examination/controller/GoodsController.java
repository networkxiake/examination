package com.xiaoyao.examination.controller;

import com.xiaoyao.examination.annotation.CheckLoginAdmin;
import com.xiaoyao.examination.controller.dto.goods.*;
import com.xiaoyao.examination.controller.form.goods.*;
import com.xiaoyao.examination.response.ResponseBody;
import com.xiaoyao.examination.response.ResponseBodyBuilder;
import com.xiaoyao.examination.service.GoodsService;
import com.xiaoyao.examination.service.StorageService;
import com.xiaoyao.examination.util.AdminStpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {
    private final GoodsService goodsService;
    private final StorageService storageService;

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
    @PostMapping("/upload-photo")
    public ResponseBody<String> uploadPhoto(MultipartFile file) {
        return ResponseBodyBuilder.build(storageService.uploadTempGoodsPhoto(AdminStpUtil.getLoginId(), file));
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

    @CheckLoginAdmin
    @PostMapping("/delete")
    public ResponseBody<Void> delete(@Valid @RequestBody DeleteForm form) {
        goodsService.deleteGoods(form.getIds());
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/upload-excel")
    public ResponseBody<Void> uploadExcel(long id, MultipartFile file) {
        goodsService.uploadExcel(id, file);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @GetMapping("/excel-url/{id}")
    public ResponseBody<String> getExcelUrl(@PathVariable long id) {
        return ResponseBodyBuilder.build(goodsService.getExcelUrl(id));
    }

    @GetMapping("/sort")
    public ResponseBody<GoodsSortDTO> sort() {
        return ResponseBodyBuilder.build(goodsService.sort());
    }

    @GetMapping("/recommend/{sort}")
    public ResponseBody<GoodsRecommendDTO> recommend(@PathVariable Integer sort, Integer count) {
        if (count == null) {
            count = 4;
        }
        return ResponseBodyBuilder.build(goodsService.recommend(sort, count));
    }
}
