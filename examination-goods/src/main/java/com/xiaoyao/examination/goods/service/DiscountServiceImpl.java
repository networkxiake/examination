package com.xiaoyao.examination.goods.service;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.common.interfaces.goods.DiscountService;
import com.xiaoyao.examination.common.interfaces.goods.GoodsService;
import com.xiaoyao.examination.common.interfaces.goods.request.CreateDiscountRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.SearchDiscountRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.UpdateDiscountRequest;
import com.xiaoyao.examination.common.interfaces.goods.response.ListDiscountResponse;
import com.xiaoyao.examination.common.interfaces.goods.response.SearchDiscountResponse;
import com.xiaoyao.examination.goods.domain.entity.Discount;
import com.xiaoyao.examination.goods.domain.service.DiscountDomainService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@DubboService
public class DiscountServiceImpl implements DiscountService {
    private final DiscountDomainService discountDomainService;
    private final ExpressRunner expressRunner;

    @DubboReference
    private GoodsService goodsService;

    public DiscountServiceImpl(DiscountDomainService discountDomainService, ExpressRunner expressRunner) {
        this.discountDomainService = discountDomainService;
        this.expressRunner = expressRunner;
    }

    @Override
    public ListDiscountResponse list() {
        List<ListDiscountResponse.Discount> discounts = new ArrayList<>();
        discountDomainService.listIdAndName().forEach(discount ->
                discounts.add(new ListDiscountResponse.Discount(String.valueOf(discount.getId()), discount.getName())));
        ListDiscountResponse response = new ListDiscountResponse();
        response.setDiscounts(discounts);
        return response;
    }

    @Override
    public void create(CreateDiscountRequest request) {
        checkScript(request.getScript());

        Discount discount = new Discount();
        discount.setName(request.getName());
        discount.setScript(request.getScript());
        discount.setDescription(request.getDescription());
        discountDomainService.save(discount);
    }

    @Override
    public SearchDiscountResponse search(SearchDiscountRequest request) {
        List<SearchDiscountResponse.Discount> discounts = new ArrayList<>();

        long[] total = new long[1];
        List<Long> discountIds = new ArrayList<>();
        for (Discount discount : discountDomainService.searchByName(request.getPage(), request.getSize(), request.getName(), total)) {
            discounts.add(new SearchDiscountResponse.Discount(String.valueOf(discount.getId()), discount.getName(), discount.getDescription()));
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

        SearchDiscountResponse response = new SearchDiscountResponse();
        response.setTotal(total[0]);
        response.setDiscounts(discounts);
        return response;
    }

    @Override
    public void update(UpdateDiscountRequest request) {
        checkScript(request.getScript());

        Discount discount = new Discount();
        discount.setId(request.getId());
        discount.setName(request.getName());
        discount.setScript(request.getScript());
        discount.setDescription(request.getDescription());
        discountDomainService.update(discount);
    }

    @Override
    public BigDecimal compute(long discountId, BigDecimal price, int count) {
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("price", price);
        context.put("count", count);

        String script = discountDomainService.getScriptByDiscountId(discountId);
        Object result = null;
        Exception exception = null;
        try {
            result = expressRunner.execute(script, context, null, true, false);
        } catch (Exception e) {
            exception = e;
        }

        if (exception == null && result instanceof BigDecimal newPrice) {
            return newPrice.setScale(2, RoundingMode.DOWN);
        } else {
            throw new ExaminationException(ErrorCode.DISCOUNT_SCRIPT_ERROR);
        }
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
