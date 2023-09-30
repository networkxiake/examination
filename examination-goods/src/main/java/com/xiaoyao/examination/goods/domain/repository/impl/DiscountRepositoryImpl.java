package com.xiaoyao.examination.goods.domain.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyao.examination.goods.domain.entity.Discount;
import com.xiaoyao.examination.goods.domain.mapper.DiscountMapper;
import com.xiaoyao.examination.goods.domain.repository.DiscountRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;
import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaUpdate;

@Repository
public class DiscountRepositoryImpl implements DiscountRepository {
    private final DiscountMapper discountMapper;

    public DiscountRepositoryImpl(DiscountMapper discountMapper) {
        this.discountMapper = discountMapper;
    }

    @Override
    public long countDiscount(Long id) {
        return discountMapper.selectCount(lambdaQuery(Discount.class)
                .eq(id != null, Discount::getId, id));
    }

    @Override
    public List<Discount> findDiscountListForList() {
        return discountMapper.selectList(lambdaQuery(Discount.class)
                .select(Discount::getId,
                        Discount::getName));
    }

    @Override
    public List<Discount> findDiscountListForSearch(long page, long size, String name, long[] total) {
        Page<Discount> discountPage = discountMapper.selectPage(Page.of(page, size), lambdaQuery(Discount.class)
                .select(Discount::getId,
                        Discount::getName,
                        Discount::getDescription)
                .eq(StrUtil.isNotBlank(name), Discount::getName, name));
        total[0] = discountPage.getTotal();
        return discountPage.getRecords();
    }

    @Override
    public String getName(long id) {
        return discountMapper.selectOne(lambdaQuery(Discount.class)
                        .select(Discount::getName)
                        .eq(Discount::getId, id))
                .getName();
    }

    @Override
    public String getScript(long id) {
        return discountMapper.selectOne(lambdaQuery(Discount.class)
                        .select(Discount::getScript)
                        .eq(Discount::getId, id))
                .getScript();
    }

    @Override
    public void save(Discount discount) {
        discountMapper.insert(discount);
    }

    @Override
    public boolean update(Discount discount) {
        Discount temp = new Discount();
        temp.setDescription(discount.getDescription());
        discount.setDescription(null);
        return discountMapper.update(discount, lambdaUpdate(Discount.class)
                .set(Discount::getDescription, temp.getDescription())) == 1;
    }
}
