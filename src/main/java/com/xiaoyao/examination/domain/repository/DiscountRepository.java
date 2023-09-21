package com.xiaoyao.examination.domain.repository;

import com.xiaoyao.examination.domain.entity.Discount;

import java.util.List;

public interface DiscountRepository {
    List<Discount> listIdAndName();

    long countDiscount(Long id);

    String getNameById(long id);

    void save(Discount discount);

    List<Discount> searchByName(long page, long size, String name, long[] total);

    boolean update(Discount discount);
}
