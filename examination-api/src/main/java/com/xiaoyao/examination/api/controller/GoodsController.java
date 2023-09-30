package com.xiaoyao.examination.api.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.xiaoyao.examination.api.annotation.CheckLoginAdmin;
import com.xiaoyao.examination.api.controller.dto.goods.*;
import com.xiaoyao.examination.api.controller.form.goods.AdminSearchForm;
import com.xiaoyao.examination.api.controller.form.goods.CreateGoodsForm;
import com.xiaoyao.examination.api.controller.form.goods.SearchGoodsForm;
import com.xiaoyao.examination.api.controller.form.goods.UpdateGoodsForm;
import com.xiaoyao.examination.api.response.ResponseBody;
import com.xiaoyao.examination.api.response.ResponseBodyBuilder;
import com.xiaoyao.examination.common.interfaces.goods.GoodsService;
import com.xiaoyao.examination.common.interfaces.goods.request.AdminSearchRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.CreateGoodsRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.SearchGoodsRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.UpdateGoodsRequest;
import com.xiaoyao.examination.common.interfaces.goods.response.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@Validated
@RequestMapping("/goods")
public class GoodsController {
    @DubboReference
    private GoodsService goodsService;

    @CheckLoginAdmin
    @PostMapping("/admin/search")
    public ResponseBody<AdminSearchGoodsDTO> search(@Valid AdminSearchForm form) {
        AdminSearchGoodsResponse response = goodsService.searchGoodsByAdmin(BeanUtil.copyProperties(form, AdminSearchRequest.class));
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(response, AdminSearchGoodsDTO.class));
    }

    @GetMapping("/type")
    public ResponseBody<GoodsTypeDTO> type() {
        AllGoodsTypeResponse response = goodsService.getAllGoodsType();
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(response, GoodsTypeDTO.class));
    }

    @CheckLoginAdmin
    @PostMapping("/apply-upload-photo")
    public ResponseBody<ApplyUploadFileDTO> applyUploadPhoto(@NotBlank String suffix) {
        ApplyUploadFileResponse response = goodsService.applyUploadPhoto(suffix);
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(response, ApplyUploadFileDTO.class));
    }

    @CheckLoginAdmin
    @PostMapping("/create")
    public ResponseBody<Void> create(@Valid @RequestBody CreateGoodsForm form) {
        CreateGoodsRequest request = BeanUtil.copyProperties(form, CreateGoodsRequest.class,
                "tag", "departmentCheckup", "laboratoryCheckup", "medicalCheckup", "otherCheckup");
        request.setTag(JSONUtil.toJsonStr(form.getTag()));
        request.setDepartmentCheckup(JSONUtil.toJsonStr(form.getDepartmentCheckup()));
        request.setLaboratoryCheckup(JSONUtil.toJsonStr(form.getLaboratoryCheckup()));
        request.setMedicalCheckup(JSONUtil.toJsonStr(form.getMedicalCheckup()));
        request.setOtherCheckup(JSONUtil.toJsonStr(form.getOtherCheckup()));
        goodsService.createGoods(request);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/change-status")
    public ResponseBody<Void> changeStatus(long id, int status) {
        goodsService.changeStatus(id, status);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @GetMapping("/{id}")
    public ResponseBody<QueryGoodsDTO> query(@PathVariable long id) {
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(goodsService.queryGoods(id), QueryGoodsDTO.class));
    }

    @CheckLoginAdmin
    @PostMapping("/update")
    public ResponseBody<Void> update(@Valid @RequestBody UpdateGoodsForm form) {
        UpdateGoodsRequest request = BeanUtil.copyProperties(form, UpdateGoodsRequest.class,
                "tag", "departmentCheckup", "laboratoryCheckup", "medicalCheckup", "otherCheckup");
        request.setTag(JSONUtil.toJsonStr(form.getTag()));
        request.setDepartmentCheckup(JSONUtil.toJsonStr(form.getDepartmentCheckup()));
        request.setLaboratoryCheckup(JSONUtil.toJsonStr(form.getLaboratoryCheckup()));
        request.setMedicalCheckup(JSONUtil.toJsonStr(form.getMedicalCheckup()));
        request.setOtherCheckup(JSONUtil.toJsonStr(form.getOtherCheckup()));
        goodsService.updateGoods(request);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/delete")
    public ResponseBody<Void> delete(@NotEmpty Long[] ids) {
        goodsService.deleteGoods(Arrays.asList(ids));
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/apply-upload-excel")
    public ResponseBody<ApplyUploadFileDTO> applyUploadExcel(long id, @NotBlank String suffix) {
        ApplyUploadFileResponse response = goodsService.applyUploadExcel(id, suffix);
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(response, ApplyUploadFileDTO.class));
    }

    @CheckLoginAdmin
    @PostMapping("/update-excel")
    public ResponseBody<Void> uploadExcel(long id, @NotBlank String path) {
        goodsService.updateExcel(id, path);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @GetMapping("/excel-url/{id}")
    public ResponseBody<String> getExcelUrl(@PathVariable long id) {
        return ResponseBodyBuilder.build(goodsService.getExcelUrl(id));
    }

    @GetMapping("/sort")
    public ResponseBody<GoodsSortDTO> sort() {
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(goodsService.sort(), GoodsSortDTO.class));
    }

    @GetMapping("/recommend/{sort}")
    public ResponseBody<SearchGoodsDTO> recommend(@PathVariable Integer sort, Integer count) {
        if (count == null) {
            count = 4;
        }
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(goodsService.recommend(sort, count), SearchGoodsDTO.class));
    }

    @PostMapping("/search")
    public ResponseBody<SearchGoodsDTO> search(@Valid SearchGoodsForm form) {
        SearchGoodsResponse response = goodsService.searchGoods(BeanUtil.copyProperties(form, SearchGoodsRequest.class));
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(response, SearchGoodsDTO.class));
    }
}
