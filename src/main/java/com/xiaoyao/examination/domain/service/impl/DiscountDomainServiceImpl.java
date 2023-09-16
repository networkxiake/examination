package com.xiaoyao.examination.domain.service.impl;

import com.xiaoyao.examination.domain.entity.Discount;
import com.xiaoyao.examination.domain.repository.DiscountRepository;
import com.xiaoyao.examination.domain.service.DiscountDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountDomainServiceImpl implements DiscountDomainService {
    private final DiscountRepository discountRepository;

    @Override
    public List<Discount> listIdAndName() {
        return discountRepository.listIdAndName();
    }

    @Override
    public boolean isIdExist(long id) {
        return discountRepository.countDiscount(id) == 1;
    }

    @Override
    public String getNameById(long id) {
        return discountRepository.getNameById(id);
    }
}
