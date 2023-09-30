package com.xiaoyao.examination.goods.domain.repository;

import com.xiaoyao.examination.goods.domain.entity.Discount;

import java.util.List;

public interface DiscountRepository {
    long countDiscount(Long id);

    List<Discount> findDiscountListForList();

    List<Discount> findDiscountListForSearch(long page, long size, String name, long[] total);

    String getName(long id);

    String getScript(long id);

    void save(Discount discount);

    boolean update(Discount discount);
}
