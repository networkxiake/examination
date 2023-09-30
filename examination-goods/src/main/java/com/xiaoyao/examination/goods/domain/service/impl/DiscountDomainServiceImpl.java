package com.xiaoyao.examination.goods.domain.service.impl;

import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.goods.domain.entity.Discount;
import com.xiaoyao.examination.goods.domain.repository.DiscountRepository;
import com.xiaoyao.examination.goods.domain.service.DiscountDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountDomainServiceImpl implements DiscountDomainService {
    private final DiscountRepository discountRepository;

    public DiscountDomainServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public List<Discount> listIdAndName() {
        return discountRepository.findDiscountListForList();
    }

    @Override
    public boolean isIdExist(long id) {
        return discountRepository.countDiscount(id) == 1;
    }

    @Override
    public String getNameById(long id) {
        return discountRepository.getName(id);
    }

    @Override
    public void save(Discount discount) {
        discountRepository.save(discount);
    }

    @Override
    public List<Discount> searchByName(long page, long size, String name, long[] total) {
        return discountRepository.findDiscountListForSearch(page, size, name, total);
    }

    @Override
    public void update(Discount discount) {
        if (!discountRepository.update(discount)) {
            throw new ExaminationException(ErrorCode.DISCOUNT_NOT_EXIST);
        }
    }

    @Override
    public String getScriptByDiscountId(long discountId) {
        return discountRepository.getScript(discountId);
    }
}
