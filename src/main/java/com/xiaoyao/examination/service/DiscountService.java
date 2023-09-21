package com.xiaoyao.examination.service;

import com.xiaoyao.examination.controller.dto.discount.ListDiscountDTO;
import com.xiaoyao.examination.controller.dto.discount.SearchDiscountDTO;
import com.xiaoyao.examination.controller.form.discount.CreateForm;
import com.xiaoyao.examination.controller.form.discount.SearchForm;
import com.xiaoyao.examination.controller.form.discount.UpdateForm;

import java.math.BigDecimal;

public interface DiscountService {
    ListDiscountDTO list();

    void create(CreateForm form);

    SearchDiscountDTO search(SearchForm form);

    void update(UpdateForm form);

    BigDecimal compute(long discountId, BigDecimal price, int count);
}
