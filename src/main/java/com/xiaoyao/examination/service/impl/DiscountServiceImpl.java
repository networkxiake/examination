package com.xiaoyao.examination.service.impl;

import com.xiaoyao.examination.controller.dto.discount.ListDiscountDTO;
import com.xiaoyao.examination.domain.service.DiscountDomainService;
import com.xiaoyao.examination.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountDomainService discountDomainService;

    @Override
    public ListDiscountDTO list() {
        List<ListDiscountDTO.Discount> discounts = new ArrayList<>();
        discountDomainService.listIdAndName().forEach(discount ->
                discounts.add(new ListDiscountDTO.Discount(String.valueOf(discount.getId()), discount.getName())));
        ListDiscountDTO dto = new ListDiscountDTO();
        dto.setDiscounts(discounts);
        return dto;
    }
}
