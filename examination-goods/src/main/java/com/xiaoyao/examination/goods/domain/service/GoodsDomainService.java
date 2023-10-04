package com.xiaoyao.examination.goods.domain.service;

import com.xiaoyao.examination.goods.domain.entity.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsDomainService {
    long createGoods(Goods goods);

    void updateSnapshotId(long goodsId, Long snapshotId);

    Map<Integer, String> getAllGoodsType();

    void changeStatus(long id, int status);

    List<Goods> searchGoodsByAdmin(long page, long size,
                                   String code, String name, Integer type, Integer status, Integer sort,
                                   long[] total);

    Goods queryGoodsById(long id);

    void updateGoods(Goods goods);

    Goods getUpdateGoodsById(long id);

    Goods findGoodsForSubmitOrder(long id);

    void deleteGoods(List<Long> ids);

    void changeFormItem(long id, String formItem);

    int getGoodsStatusById(long id);

    Map<Integer, String> getAllGoodsSort();

    List<Goods> getRecommendGoods(int sort, int count);

    List<Goods> searchGoods(int pass, int size, String name, Integer type, String gender,
                            String bottomPrice, String topPrice, String order);

    Goods getSnapshotGoodsById(long goodsId);

    Map<Long, Long> countGoodsByDiscountIds(List<Long> discountIds);

    void increaseSales(long goodsId, int count);

    long countDontDeletedGoods(List<Long> ids);

    void decreaseSales(long goodsId, int count);
}
