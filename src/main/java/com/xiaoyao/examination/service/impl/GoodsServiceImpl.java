package com.xiaoyao.examination.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.xiaoyao.examination.controller.dto.goods.*;
import com.xiaoyao.examination.controller.form.goods.AdminSearchForm;
import com.xiaoyao.examination.controller.form.goods.CreateForm;
import com.xiaoyao.examination.controller.form.goods.SearchGoodsForm;
import com.xiaoyao.examination.controller.form.goods.UpdateForm;
import com.xiaoyao.examination.domain.entity.Goods;
import com.xiaoyao.examination.domain.entity.GoodsSnapshot;
import com.xiaoyao.examination.domain.enums.GoodsStatus;
import com.xiaoyao.examination.domain.service.DiscountDomainService;
import com.xiaoyao.examination.domain.service.GoodsDomainService;
import com.xiaoyao.examination.domain.service.GoodsSnapshotDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.service.GoodsService;
import com.xiaoyao.examination.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final GoodsDomainService goodsDomainService;
    private final DiscountDomainService discountDomainService;
    private final StorageService storageService;
    private final GoodsSnapshotDomainService snapshotDomainService;

    @Override
    public void createGoods(CreateForm form) {
        // 判断折扣类别是否存在
        if (form.getDiscountId() != null && !discountDomainService.isIdExist(form.getDiscountId())) {
            throw new ExaminationException(ErrorCode.DISCOUNT_NOT_EXIST);
        }

        Goods goods = BeanUtil.copyProperties(form, Goods.class);
        goods.setTag(JSONUtil.toJsonStr(form.getTag()));
        goods.setDepartmentCheckup(JSONUtil.toJsonStr(form.getDepartmentCheckup()));
        goods.setLaboratoryCheckup(JSONUtil.toJsonStr(form.getLaboratoryCheckup()));
        goods.setMedicalCheckup(JSONUtil.toJsonStr(form.getMedicalCheckup()));
        goods.setOtherCheckup(JSONUtil.toJsonStr(form.getOtherCheckup()));
        long goodsId = goodsDomainService.createGoods(goods);

        storageService.confirmTempFile(form.getImage());
        saveSnapshot(goodsId);
    }

    @Async
    public void saveSnapshot(long goodsId) {
        Goods goods = goodsDomainService.getSnapshotGoodsById(goodsId);

        GoodsSnapshot snapshot = new GoodsSnapshot();
        snapshot.setGoodsId(goods.getId());
        snapshot.setName(goods.getName());
        snapshot.setDescription(goods.getDescription());
        snapshot.setImage(goods.getImage());
        snapshot.setOriginalPrice(goods.getOriginalPrice());
        snapshot.setCurrentPrice(goods.getCurrentPrice());
        snapshot.setType(goods.getType());
        snapshot.setTag(goods.getTag());
        snapshot.setDepartmentCheckup(goods.getDepartmentCheckup());
        snapshot.setLaboratoryCheckup(goods.getLaboratoryCheckup());
        snapshot.setMedicalCheckup(goods.getMedicalCheckup());
        snapshot.setOtherCheckup(goods.getOtherCheckup());
        snapshot.setUpdateTime(goods.getUpdateTime());
        snapshot.setCreateTime(goods.getCreateTime());
        snapshotDomainService.saveSnapshot(snapshot);
    }

    @Override
    public GoodsTypeDTO getAllGoodsType() {
        // TODO 添加缓存
        List<GoodsTypeDTO.Type> types = new ArrayList<>();
        goodsDomainService.getAllGoodsType().forEach((key, value) ->
                types.add(new GoodsTypeDTO.Type(key, value)));

        GoodsTypeDTO dto = new GoodsTypeDTO();
        dto.setTypes(types);
        return dto;
    }

    @Override
    public void changeStatus(long id, int status) {
        goodsDomainService.changeStatus(id, status);
    }

    @Override
    public AdminSearchGoodsDTO searchGoodsByAdmin(AdminSearchForm form) {
        long[] total = new long[1];
        List<AdminSearchGoodsDTO.Goods> result = new ArrayList<>();

        goodsDomainService.searchGoodsByAdmin(form.getPage(), form.getSize(),
                form.getCode(), form.getName(), form.getType(), form.getStatus(), form.getSort(), total).forEach(item -> {
            AdminSearchGoodsDTO.Goods goods = new AdminSearchGoodsDTO.Goods();
            goods.setId(String.valueOf(item.getId()));
            goods.setName(item.getName());
            goods.setCode(item.getCode());
            goods.setOriginalPrice(item.getOriginalPrice().toString());
            goods.setCurrentPrice(item.getCurrentPrice().toString());
            goods.setDiscountId(item.getDiscountId() != null ? String.valueOf(item.getDiscountId()) : null);
            goods.setSalesVolume(item.getSalesVolume());
            goods.setType(item.getType());
            goods.setStatus(item.getStatus());
            goods.setHasExcel(item.getFormItem() != null);
            result.add(goods);
        });

        AdminSearchGoodsDTO dto = new AdminSearchGoodsDTO();
        dto.setTotal(total[0]);
        dto.setResults(result);
        return dto;
    }

    @Override
    public QueryGoodsDTO queryGoods(long id) {
        Goods goods = goodsDomainService.queryGoodsById(id);
        if (goods == null) {
            throw new ExaminationException(ErrorCode.GOODS_NOT_EXIST);
        }

        QueryGoodsDTO dto = new QueryGoodsDTO();
        dto.setName(goods.getName());
        dto.setCode(goods.getCode());
        dto.setDescription(goods.getDescription());
        dto.setOriginalPrice(goods.getOriginalPrice().toString());
        dto.setCurrentPrice(goods.getCurrentPrice().toString());
        dto.setDiscountId(goods.getDiscountId() != null ? String.valueOf(goods.getDiscountId()) : null);
        dto.setImage(storageService.getPathDownloadingUrl(goods.getImage()));
        dto.setType(goods.getType());
        dto.setSort(goods.getSort());
        dto.setTag(JSONUtil.parseArray(goods.getTag()).toList(String.class));
        if (goods.getDepartmentCheckup() != null) {
            dto.setDepartmentCheckup(JSONUtil.parseArray(goods.getDepartmentCheckup()).toList(QueryGoodsDTO.Item.class));
        }
        if (goods.getLaboratoryCheckup() != null) {
            dto.setLaboratoryCheckup(JSONUtil.parseArray(goods.getLaboratoryCheckup()).toList(QueryGoodsDTO.Item.class));
        }
        if (goods.getMedicalCheckup() != null) {
            dto.setMedicalCheckup(JSONUtil.parseArray(goods.getMedicalCheckup()).toList(QueryGoodsDTO.Item.class));
        }
        if (goods.getOtherCheckup() != null) {
            dto.setOtherCheckup(JSONUtil.parseArray(goods.getOtherCheckup()).toList(QueryGoodsDTO.Item.class));
        }
        return dto;
    }

    @Override
    public void updateGoods(UpdateForm form) {
        // 判断折扣类别是否存在
        if (form.getDiscountId() != null && !discountDomainService.isIdExist(form.getDiscountId())) {
            throw new ExaminationException(ErrorCode.DISCOUNT_NOT_EXIST);
        }

        Goods rawGoods = goodsDomainService.getUpdateGoodsById(form.getId());
        // 套餐已下架才可以修改
        if (rawGoods.getStatus() == GoodsStatus.ON.getStatus()) {
            throw new ExaminationException(ErrorCode.GOODS_STATUS_NOT_ALLOW_UPDATE);
        }

        Goods goods = BeanUtil.copyProperties(form, Goods.class);
        if (form.getTag() != null) {
            goods.setTag(JSONUtil.toJsonStr(form.getTag()));
        }
        if (form.getDepartmentCheckup() != null) {
            goods.setDepartmentCheckup(JSONUtil.toJsonStr(form.getDepartmentCheckup()));
        }
        if (form.getLaboratoryCheckup() != null) {
            goods.setLaboratoryCheckup(JSONUtil.toJsonStr(form.getLaboratoryCheckup()));
        }
        if (form.getMedicalCheckup() != null) {
            goods.setMedicalCheckup(JSONUtil.toJsonStr(form.getMedicalCheckup()));
        }
        if (form.getOtherCheckup() != null) {
            goods.setOtherCheckup(JSONUtil.toJsonStr(form.getOtherCheckup()));
        }
        goodsDomainService.updateGoods(goods);
        saveSnapshot(form.getId());
    }

    @Override
    public void deleteGoods(List<Long> ids) {
        goodsDomainService.deleteGoods(ids);
        // 删除体检套餐时不能删除封面图片，因为图片已经归档了，但可以删除Excel文件，因为Excel文件不需要归档。
        storageService.deleteExcel(ids);
    }

    @Override
    public void uploadExcel(long goodsId, MultipartFile file) {
        // 套餐已下架才可以修改
        if (goodsDomainService.getGoodsStatusById(goodsId) == GoodsStatus.ON.getStatus()) {
            throw new ExaminationException(ErrorCode.GOODS_STATUS_NOT_ALLOW_UPDATE);
        }

        goodsDomainService.changeFormItem(goodsId, parseExcel(file));
        storageService.uploadExcel(goodsId, file);
    }

    private String parseExcel(MultipartFile file) {
        // TODO 解析excel
        return "[1,2,3]";
    }

    @Override
    public String getExcelUrl(long goodsId) {
        return storageService.getExcelDownloadingUrl(goodsId);
    }

    @Override
    public GoodsSortDTO sort() {
        // TODO 添加缓存
        List<GoodsSortDTO.Sort> sorts = new ArrayList<>();
        goodsDomainService.getAllGoodsSort().forEach((key, value) ->
                sorts.add(new GoodsSortDTO.Sort(key, value)));

        GoodsSortDTO dto = new GoodsSortDTO();
        dto.setSorts(sorts);
        return dto;
    }

    @Override
    public SearchGoodsDTO recommend(int sort, int count) {
        // TODO 添加缓存
        List<SearchGoodsDTO.Goods> goodsList = new ArrayList<>();
        goodsDomainService.getRecommendGoods(sort, count).forEach(item -> {
            SearchGoodsDTO.Goods goods = new SearchGoodsDTO.Goods();
            goods.setId(String.valueOf(item.getId()));
            goods.setName(item.getName());
            goods.setDescription(item.getDescription());
            goods.setImage(storageService.getPathDownloadingUrl(item.getImage()));
            goods.setOriginalPrice(item.getOriginalPrice().toString());
            goods.setCurrentPrice(item.getCurrentPrice().toString());
            goods.setSalesVolume(item.getSalesVolume());
            goods.setDiscountId(item.getDiscountId() != null ? String.valueOf(item.getDiscountId()) : null);
            goodsList.add(goods);
        });

        SearchGoodsDTO dto = new SearchGoodsDTO();
        dto.setGoodsList(goodsList);
        return dto;
    }

    @Override
    public SearchGoodsDTO searchGoods(SearchGoodsForm form) {
        // TODO 添加缓存
        List<SearchGoodsDTO.Goods> goodsList = new ArrayList<>();
        goodsDomainService.searchGoods(form.getPass(), form.getSize(), form.getName(), form.getType(),
                form.getGender(), form.getBottomPrice(), form.getTopPrice(), form.getOrder()).forEach(item -> {
            SearchGoodsDTO.Goods goods = new SearchGoodsDTO.Goods();
            goods.setId(String.valueOf(item.getId()));
            goods.setName(item.getName());
            goods.setDescription(item.getDescription());
            goods.setImage(storageService.getPathDownloadingUrl(item.getImage()));
            goods.setOriginalPrice(item.getOriginalPrice().toString());
            goods.setCurrentPrice(item.getCurrentPrice().toString());
            goods.setSalesVolume(item.getSalesVolume());
            goods.setDiscountId(item.getDiscountId() != null ? String.valueOf(item.getDiscountId()) : null);
            goodsList.add(goods);
        });

        SearchGoodsDTO dto = new SearchGoodsDTO();
        dto.setGoodsList(goodsList);
        return dto;
    }

    @Override
    public Map<Long, Long> countGoodsByDiscountIds(List<Long> discountIds) {
        return goodsDomainService.countGoodsByDiscountIds(discountIds);
    }
}
