package com.xiaoyao.examination.domain.service;

import com.xiaoyao.examination.domain.entity.Discount;

import java.util.List;

public interface DiscountDomainService {
    List<Discount> listIdAndName();

    boolean isIdExist(long id);
}
