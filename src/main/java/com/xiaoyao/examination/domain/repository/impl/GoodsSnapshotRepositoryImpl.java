package com.xiaoyao.examination.domain.repository.impl;

import com.xiaoyao.examination.domain.entity.GoodsSnapshot;
import com.xiaoyao.examination.domain.mapper.GoodsSnapshotMapper;
import com.xiaoyao.examination.domain.repository.GoodsSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GoodsSnapshotRepositoryImpl implements GoodsSnapshotRepository {
    private final GoodsSnapshotMapper goodsSnapshotMapper;

    @Override
    public void save(GoodsSnapshot snapshot) {
        goodsSnapshotMapper.insert(snapshot);
    }
}
