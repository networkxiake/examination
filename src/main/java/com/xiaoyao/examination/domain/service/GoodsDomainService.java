package com.xiaoyao.examination.domain.service;

import com.xiaoyao.examination.domain.entity.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsDomainService {
    void createGoods(Goods goods);

    Map<Integer, String> getAllGoodsType();

    String getGoodsTypeById(int id);

    void changeStatus(long id, int status);

    List<Goods> searchGoods(long page, long size,
                            String code, String name, Integer type, Integer status,
                            long[] total);

    Goods queryGoodsById(long id);
}
