package com.xiaoyao.examination.domain.repository.impl;

import com.xiaoyao.examination.domain.entity.GoodsSnapshot;
import com.xiaoyao.examination.domain.mapper.GoodsSnapshotMapper;
import com.xiaoyao.examination.domain.repository.GoodsSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

@Repository
@RequiredArgsConstructor
public class GoodsSnapshotRepositoryImpl implements GoodsSnapshotRepository {
    private final GoodsSnapshotMapper goodsSnapshotMapper;

    @Override
    public void save(GoodsSnapshot snapshot) {
        goodsSnapshotMapper.insert(snapshot);
    }

    @Override
    public long queryNewestSnapshotId(long goodsId, LocalDateTime updateTime) {
        return goodsSnapshotMapper.selectOne(lambdaQuery(GoodsSnapshot.class)
                        .select(GoodsSnapshot::getId)
                        .eq(GoodsSnapshot::getGoodsId, goodsId)
                        .eq(GoodsSnapshot::getUpdateTime, updateTime))
                .getId();
    }
}
