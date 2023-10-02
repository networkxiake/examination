package com.xiaoyao.examination.goods.domain.repository;

import com.xiaoyao.examination.goods.domain.entity.Goods;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Map;

public interface GoodsRepository {
    long countGoodsInIdsAndStatus(List<Long> ids, int status);

    List<Goods> findGoodsListForAdminSearch(long page, long size, long[] total, Goods goods);

    List<Goods> findGoodsListForUserRecommend(int sort, int count);

    List<Goods> findGoodsListForUserSearch(int pass, int size, String name, Integer type, String gender,
                                           String bottomPrice, String topPrice, String order);

    Goods findGoodsForUpdate(long id);

    Goods findGoodsForPreUpdate(long id);

    Goods findGoodsForSnapshot(long id);

    Goods findGoodsForSubmitOrder(long id);

    int getGoodsStatus(long id);

    Map<Long, Long> getGoodsCountInDiscountIds(List<Long> discountIds);

    /**
     * 查询套餐中是否存在指定的编号。
     *
     * @param code    要查询的编号
     * @param goodsId 当前套餐的id，如果指定了则排除当前套餐。
     */
    boolean isExistCode(String code, @Nullable Long goodsId);

    void save(Goods goods);

    void delete(List<Long> ids);

    void update(Goods goods);

    void updateSnapshotId(long id, Long snapshotId);

    void updateStatus(long id, int status);

    void updateSalesVolume(long id, int count);
}
