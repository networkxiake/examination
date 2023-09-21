package com.xiaoyao.examination.domain.repository;

import com.xiaoyao.examination.domain.entity.GoodsSnapshot;

import java.time.LocalDateTime;

public interface GoodsSnapshotRepository {
    void save(GoodsSnapshot snapshot);

    long queryNewestSnapshotId(long goodsId, LocalDateTime updateTime);
}
