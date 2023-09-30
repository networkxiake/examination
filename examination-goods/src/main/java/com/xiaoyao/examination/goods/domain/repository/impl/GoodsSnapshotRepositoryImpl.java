package com.xiaoyao.examination.goods.domain.repository.impl;

import com.xiaoyao.examination.goods.domain.entity.GoodsSnapshot;
import com.xiaoyao.examination.goods.domain.mapper.GoodsSnapshotMapper;
import com.xiaoyao.examination.goods.domain.repository.GoodsSnapshotRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsSnapshotRepositoryImpl implements GoodsSnapshotRepository {
    private final GoodsSnapshotMapper goodsSnapshotMapper;

    public GoodsSnapshotRepositoryImpl(GoodsSnapshotMapper goodsSnapshotMapper) {
        this.goodsSnapshotMapper = goodsSnapshotMapper;
    }

    @Override
    public void save(GoodsSnapshot snapshot) {
        goodsSnapshotMapper.insert(snapshot);
    }
}
