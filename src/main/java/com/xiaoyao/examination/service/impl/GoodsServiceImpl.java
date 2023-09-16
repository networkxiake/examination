package com.xiaoyao.examination.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.xiaoyao.examination.controller.dto.goods.GoodsTypeDTO;
import com.xiaoyao.examination.controller.dto.goods.QueryGoodsDTO;
import com.xiaoyao.examination.controller.dto.goods.SearchGoodsDTO;
import com.xiaoyao.examination.controller.form.goods.CreateForm;
import com.xiaoyao.examination.controller.form.goods.SearchForm;
import com.xiaoyao.examination.controller.form.goods.UpdateForm;
import com.xiaoyao.examination.domain.entity.Goods;
import com.xiaoyao.examination.domain.enums.GoodsStatus;
import com.xiaoyao.examination.domain.service.DiscountDomainService;
import com.xiaoyao.examination.domain.service.GoodsDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.service.GoodsService;
import com.xiaoyao.examination.service.StorageService;
import com.xiaoyao.examination.service.event.FileChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final GoodsDomainService goodsDomainService;
    private final DiscountDomainService discountDomainService;
    private final StorageService storageService;
    private final ApplicationEventMulticaster eventMulticaster;

    @Override
    public void createGoods(CreateForm form) {
        // 判断折扣类别是否存在
        if (form.getDiscountId() != null && !discountDomainService.isIdExist(form.getDiscountId())) {
            throw new ExaminationException(ErrorCode.DISCOUNT_NOT_EXIST);
        }

        Goods goods = BeanUtil.copyProperties(form, Goods.class);
        goods.setTag(JSONUtil.toJsonPrettyStr(form.getTag()));
        goods.setDepartmentCheckup(JSONUtil.toJsonPrettyStr(form.getDepartmentCheckup()));
        goods.setLaboratoryCheckup(JSONUtil.toJsonPrettyStr(form.getLaboratoryCheckup()));
        goods.setMedicalCheckup(JSONUtil.toJsonPrettyStr(form.getMedicalCheckup()));
        goods.setOtherCheckup(JSONUtil.toJsonPrettyStr(form.getOtherCheckup()));
        goodsDomainService.createGoods(goods);
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
    public SearchGoodsDTO searchGoods(SearchForm form) {
        long[] total = new long[1];
        List<SearchGoodsDTO.Goods> result = new ArrayList<>();

        Map<Integer, String> goodsType = goodsDomainService.getAllGoodsType();
        Map<Long, String> discounts = new HashMap<>();
        discountDomainService.listIdAndName().forEach(item -> discounts.put(item.getId(), item.getName()));

        goodsDomainService.searchGoods(form.getPage(), form.getSize(),
                form.getCode(), form.getName(), form.getType(), form.getStatus(), total).forEach(item -> {
            SearchGoodsDTO.Goods goods = new SearchGoodsDTO.Goods();
            goods.setId(String.valueOf(item.getId()));
            goods.setName(item.getName());
            goods.setCode(item.getCode());
            goods.setOriginalPrice(item.getOriginalPrice().toString());
            goods.setCurrentPrice(item.getCurrentPrice().toString());
            // 根据折扣id获取折扣名称
            goods.setDiscount(discounts.get(item.getDiscountId()));
            goods.setSalesVolume(item.getSalesVolume());
            goods.setType(goodsType.get(item.getType()));
            goods.setStatus(item.getStatus());
            result.add(goods);
        });

        SearchGoodsDTO dto = new SearchGoodsDTO();
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
        if (goods.getDiscountId() != null) {
            dto.setDiscount(discountDomainService.getNameById(goods.getDiscountId()));
        }
        dto.setImage(storageService.getPathUrl(goods.getImage()));
        dto.setType(goodsDomainService.getGoodsTypeById(goods.getType()));
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
        // 确保真的需要更新
        if (BeanUtil.beanToMap(form, false, true).size() == 1) {
            return;
        }

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
            goods.setTag(JSONUtil.toJsonPrettyStr(form.getTag()));
        }
        if (form.getDepartmentCheckup() != null) {
            goods.setDepartmentCheckup(JSONUtil.toJsonPrettyStr(form.getDepartmentCheckup()));
        }
        if (form.getLaboratoryCheckup() != null) {
            goods.setLaboratoryCheckup(JSONUtil.toJsonPrettyStr(form.getLaboratoryCheckup()));
        }
        if (form.getMedicalCheckup() != null) {
            goods.setMedicalCheckup(JSONUtil.toJsonPrettyStr(form.getMedicalCheckup()));
        }
        if (form.getOtherCheckup() != null) {
            goods.setOtherCheckup(JSONUtil.toJsonPrettyStr(form.getOtherCheckup()));
        }
        goodsDomainService.updateGoods(goods);

        if (form.getImage() != null) {
            eventMulticaster.multicastEvent(new FileChangedEvent(rawGoods.getImage(), form.getImage()));
        }
    }

    @Override
    public void deleteGoods(List<Long> ids) {
        goodsDomainService.deleteGoods(ids);
    }
}
