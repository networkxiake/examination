package com.xiaoyao.examination.goods.domain.service;

import com.xiaoyao.examination.goods.domain.entity.Discount;

import java.util.List;

public interface DiscountDomainService {
    List<Discount> listIdAndName();

    boolean isIdExist(long id);

    String getNameById(long id);

    void save(Discount discount);

    List<Discount> searchByName(long page, long size, String name, long[] total);

    void update(Discount discount);

    String getScriptByDiscountId(long discountId);
}
