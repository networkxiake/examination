package com.xiaoyao.examination.common.interfaces.goods;

import com.xiaoyao.examination.common.interfaces.goods.request.CreateDiscountRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.SearchDiscountRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.UpdateDiscountRequest;
import com.xiaoyao.examination.common.interfaces.goods.response.ListDiscountResponse;
import com.xiaoyao.examination.common.interfaces.goods.response.SearchDiscountResponse;

import java.math.BigDecimal;

public interface DiscountService {
    ListDiscountResponse list();

    void create(CreateDiscountRequest request);

    SearchDiscountResponse search(SearchDiscountRequest request);

    void update(UpdateDiscountRequest request);

    BigDecimal compute(long discountId, BigDecimal price, int count);
}
