package com.xiaoyao.examination.goods.domain.repository;

import com.xiaoyao.examination.goods.domain.entity.Goods;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Map;

public interface GoodsRepository {
    void insert(Goods goods);

    void updateSnapshot(long goodsId, String md5);

    void update(Goods goods);

    void changeStatus(long id, int status);

    List<Goods> searchGoods(long page, long size,
                            String code, String name, Integer type, Integer status, Integer sort,
                            long[] total);

    /**
     * 查询套餐中是否存在指定的编号。
     *
     * @param code    要查询的编号
     * @param goodsId 当前套餐的id，如果指定了则排除当前套餐。
     */
    boolean isCodeExist(String code, @Nullable Long goodsId);

    Goods queryGoodsById(long id);

    Goods getUpdateGoodsById(long id);

    void deleteGoods(List<Long> ids);

    long countDontDeletedGoods(List<Long> ids);

    int getGoodsStatusById(long id);

    List<Goods> getRecommendGoods(int sort, int count);

    List<Goods> searchGoodsByPage(int pass, int size, String name, Integer type, String gender,
                                  String bottomPrice, String topPrice, String order);

    Goods getSnapshotGoodsById(long goodsId);

    Map<Long, Long> countGoodsByDiscountIds(List<Long> discountIds);

    Goods getOrderGoodsById(long id);

    void increaseSales(long goodsId, int count);
}
