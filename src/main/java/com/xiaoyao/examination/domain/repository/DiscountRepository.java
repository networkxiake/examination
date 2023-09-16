package com.xiaoyao.examination.domain.repository;

import com.xiaoyao.examination.domain.entity.Discount;

import java.util.List;

public interface DiscountRepository {
    List<Discount> listIdAndName();

    long countDiscount(Long id);
}
