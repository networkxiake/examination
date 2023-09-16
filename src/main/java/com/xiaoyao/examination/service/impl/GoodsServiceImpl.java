package com.xiaoyao.examination.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xiaoyao.examination.controller.dto.goods.GoodsTypeDTO;
import com.xiaoyao.examination.controller.dto.goods.SearchGoodsDTO;
import com.xiaoyao.examination.controller.form.goods.CreateForm;
import com.xiaoyao.examination.controller.form.goods.SearchForm;
import com.xiaoyao.examination.domain.entity.Goods;
import com.xiaoyao.examination.domain.service.DiscountDomainService;
import com.xiaoyao.examination.domain.service.GoodsDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.service.GoodsService;
import lombok.RequiredArgsConstructor;
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

    @Override
    public void createGoods(CreateForm form) {
        // 判断折扣类别是否存在
        if (!discountDomainService.isIdExist(form.getDiscountId())) {
            throw new ExaminationException(ErrorCode.DISCOUNT_NOT_EXIST);
        }
        goodsDomainService.createGoods(BeanUtil.copyProperties(form, Goods.class));
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
}
