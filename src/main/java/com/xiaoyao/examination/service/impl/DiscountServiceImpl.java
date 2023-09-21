package com.xiaoyao.examination.service.impl;

import com.ql.util.express.ExpressRunner;
import com.xiaoyao.examination.controller.dto.discount.ListDiscountDTO;
import com.xiaoyao.examination.controller.dto.discount.SearchDiscountDTO;
import com.xiaoyao.examination.controller.form.discount.CreateForm;
import com.xiaoyao.examination.controller.form.discount.SearchForm;
import com.xiaoyao.examination.controller.form.discount.UpdateForm;
import com.xiaoyao.examination.domain.entity.Discount;
import com.xiaoyao.examination.domain.service.DiscountDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.service.DiscountService;
import com.xiaoyao.examination.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountDomainService discountDomainService;
    private final ExpressRunner expressRunner;
    private final GoodsService goodsService;

    @Override
    public ListDiscountDTO list() {
        List<ListDiscountDTO.Discount> discounts = new ArrayList<>();
        discountDomainService.listIdAndName().forEach(discount ->
                discounts.add(new ListDiscountDTO.Discount(String.valueOf(discount.getId()), discount.getName())));
        ListDiscountDTO dto = new ListDiscountDTO();
        dto.setDiscounts(discounts);
        return dto;
    }

    @Override
    public void create(CreateForm form) {
        checkScript(form.getScript());

        Discount discount = new Discount();
        discount.setName(form.getName());
        discount.setScript(form.getScript());
        discount.setDescription(form.getDescription());
        discountDomainService.save(discount);
    }

    @Override
    public SearchDiscountDTO search(SearchForm form) {
        List<SearchDiscountDTO.Discount> discounts = new ArrayList<>();

        long[] total = new long[1];
        List<Long> discountIds = new ArrayList<>();
        for (Discount discount : discountDomainService.searchByName(form.getPage(), form.getSize(), form.getName(), total)) {
            discounts.add(new SearchDiscountDTO.Discount(String.valueOf(discount.getId()), discount.getName(), discount.getDescription()));
            discountIds.add(discount.getId());
        }
        if (!discountIds.isEmpty()) {
            goodsService.countGoodsByDiscountIds(discountIds).forEach((discountId, count) ->
                    discounts.forEach(discount -> {
                        if (discount.getId().equals(String.valueOf(discountId))) {
                            discount.setGoodsCount(count);
                        }
                    }));
        }

        SearchDiscountDTO dto = new SearchDiscountDTO();
        dto.setTotal(total[0]);
        dto.setDiscounts(discounts);
        return dto;
    }

    @Override
    public void update(UpdateForm form) {
        checkScript(form.getScript());

        Discount discount = new Discount();
        discount.setId(form.getId());
        discount.setName(form.getName());
        discount.setScript(form.getScript());
        discount.setDescription(form.getDescription());
        discountDomainService.update(discount);
    }

    private void checkScript(String script) {
        if (script != null && !script.isEmpty()) {
            try {
                expressRunner.parseInstructionSet(script);
            } catch (Exception e) {
                throw new ExaminationException(ErrorCode.DISCOUNT_SCRIPT_ERROR);
            }
        }
    }
}
