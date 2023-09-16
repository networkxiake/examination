package com.xiaoyao.examination.domain.repository;

import com.xiaoyao.examination.domain.entity.Goods;

import java.util.List;

public interface GoodsRepository {
    void insert(Goods goods);

    void update(Goods goods);

    List<Goods> searchGoods(long page, long size,
                            String code, String name, Integer type, Integer status,
                            long[] total);

    long countGoods(String name, String code);

    Goods queryGoodsById(long id);

    Goods getUpdateGoodsById(long id);
}
