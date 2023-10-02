package com.xiaoyao.examination.goods.domain.repository.impl;

import com.xiaoyao.examination.goods.domain.entity.GoodsSnapshot;
import com.xiaoyao.examination.goods.domain.mapper.GoodsSnapshotMapper;
import com.xiaoyao.examination.goods.domain.repository.GoodsSnapshotRepository;
import org.springframework.stereotype.Repository;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

@Repository
public class GoodsSnapshotRepositoryImpl implements GoodsSnapshotRepository {
    private final GoodsSnapshotMapper goodsSnapshotMapper;

    public GoodsSnapshotRepositoryImpl(GoodsSnapshotMapper goodsSnapshotMapper) {
        this.goodsSnapshotMapper = goodsSnapshotMapper;
    }

    @Override
    public String getSnapshotMd5(long goodsId) {
        return goodsSnapshotMapper.selectOne(lambdaQuery(GoodsSnapshot.class)
                        .eq(GoodsSnapshot::getGoodsId, goodsId))
                .getSnapshotMd5();
    }

    @Override
    public void save(GoodsSnapshot snapshot) {
        goodsSnapshotMapper.insert(snapshot);
    }
}
