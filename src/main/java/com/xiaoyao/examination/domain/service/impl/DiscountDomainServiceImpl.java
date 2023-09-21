package com.xiaoyao.examination.domain.service.impl;

import com.xiaoyao.examination.domain.entity.Discount;
import com.xiaoyao.examination.domain.repository.DiscountRepository;
import com.xiaoyao.examination.domain.service.DiscountDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
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

    @Override
    public void save(Discount discount) {
        discountRepository.save(discount);
    }

    @Override
    public List<Discount> searchByName(long page, long size, String name, long[] total) {
        return discountRepository.searchByName(page, size, name, total);
    }

    @Override
    public void update(Discount discount) {
        if (!discountRepository.update(discount)) {
            throw new ExaminationException(ErrorCode.DISCOUNT_NOT_EXIST);
        }
    }
}
