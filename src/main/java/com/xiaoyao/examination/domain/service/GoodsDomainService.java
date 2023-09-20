package com.xiaoyao.examination.domain.service;

import com.xiaoyao.examination.domain.entity.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsDomainService {
    long createGoods(Goods goods);

    Map<Integer, String> getAllGoodsType();

    String getGoodsTypeById(int id);

    void changeStatus(long id, int status);

    List<Goods> searchGoodsByAdmin(long page, long size,
                                   String code, String name, Integer type, Integer status, Integer sort,
                                   long[] total);

    Goods queryGoodsById(long id);

    void updateGoods(Goods goods);

    Goods getUpdateGoodsById(long id);

    void deleteGoods(List<Long> ids);

    void changeFormItem(long id, String formItem);

    int getGoodsStatusById(long id);

    Map<Integer, String> getAllGoodsSort();

    List<Goods> getRecommendGoods(int sort, int count);

    List<Goods> searchGoods(int pass, int size, String name, Integer type, String gender,
                            String bottomPrice, String topPrice, String order);

    Goods getSnapshotGoodsById(long goodsId);
}
