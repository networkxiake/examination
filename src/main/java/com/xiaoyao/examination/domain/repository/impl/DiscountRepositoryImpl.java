package com.xiaoyao.examination.domain.repository.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyao.examination.domain.entity.Discount;
import com.xiaoyao.examination.domain.mapper.DiscountMapper;
import com.xiaoyao.examination.domain.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;
import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaUpdate;

@Repository
@RequiredArgsConstructor
public class DiscountRepositoryImpl implements DiscountRepository {
    private final DiscountMapper discountMapper;

    @Override
    public List<Discount> listIdAndName() {
        return discountMapper.selectList(lambdaQuery(Discount.class)
                .select(Discount::getId,
                        Discount::getName));
    }

    @Override
    public long countDiscount(Long id) {
        return discountMapper.selectCount(lambdaQuery(Discount.class)
                .eq(id != null, Discount::getId, id));
    }

    @Override
    public String getNameById(long id) {
        return discountMapper.selectOne(lambdaQuery(Discount.class)
                        .select(Discount::getName)
                        .eq(Discount::getId, id))
                .getName();
    }

    @Override
    public void save(Discount discount) {
        discountMapper.insert(discount);
    }

    @Override
    public List<Discount> searchByName(long page, long size, String name, long[] total) {
        Page<Discount> discountPage = discountMapper.selectPage(Page.of(page, size), lambdaQuery(Discount.class)
                .select(Discount::getId,
                        Discount::getName,
                        Discount::getDescription)
                .eq(name != null, Discount::getName, name));
        total[0] = discountPage.getTotal();
        return discountPage.getRecords();
    }

    @Override
    public boolean update(Discount discount) {
        Discount temp = new Discount();
        temp.setDescription(discount.getDescription());
        discount.setDescription(null);
        return discountMapper.update(discount, lambdaUpdate(Discount.class)
                .set(Discount::getDescription, temp.getDescription())) == 1;
    }

    @Override
    public String getScriptByDiscountId(long discountId) {
        return discountMapper.selectOne(lambdaQuery(Discount.class)
                        .select(Discount::getScript)
                        .eq(Discount::getId, discountId))
                .getScript();
    }
}
