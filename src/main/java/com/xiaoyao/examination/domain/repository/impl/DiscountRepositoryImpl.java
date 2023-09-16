package com.xiaoyao.examination.domain.repository.impl;

import com.xiaoyao.examination.domain.entity.Discount;
import com.xiaoyao.examination.domain.mapper.DiscountMapper;
import com.xiaoyao.examination.domain.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

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
}
