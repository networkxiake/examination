package com.xiaoyao.examination.domain.repository.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyao.examination.domain.entity.Goods;
import com.xiaoyao.examination.domain.mapper.GoodsMapper;
import com.xiaoyao.examination.domain.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

@Repository
@RequiredArgsConstructor
public class GoodsRepositoryImpl implements GoodsRepository {
    private final GoodsMapper goodsMapper;

    @Override
    public void insert(Goods goods) {
        goodsMapper.insert(goods);
    }

    @Override
    public void update(Goods goods) {
        goodsMapper.updateById(goods);
    }

    @Override
    public List<Goods> searchGoods(long page, long size,
                                   String code, String name, Integer type, Integer status,
                                   long[] total) {
        Page<Goods> goodsPage = goodsMapper.selectPage(Page.of(page, size), lambdaQuery(Goods.class)
                .select(Goods::getId,
                        Goods::getName,
                        Goods::getCode,
                        Goods::getOriginalPrice,
                        Goods::getCurrentPrice,
                        Goods::getDiscountId,
                        Goods::getSalesVolume,
                        Goods::getType,
                        Goods::getStatus)
                .eq(code != null, Goods::getCode, code)
                .eq(name != null, Goods::getName, name)
                .eq(type != null, Goods::getType, type)
                .eq(status != null, Goods::getStatus, status));

        total[0] = goodsPage.getTotal();
        return goodsPage.getRecords();
    }

    @Override
    public long countGoods(String name) {
        return goodsMapper.selectCount(lambdaQuery(Goods.class)
                .eq(name != null, Goods::getName, name));
    }
}
