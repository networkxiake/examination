package com.xiaoyao.examination.domain.service;

import com.xiaoyao.examination.domain.entity.GoodsSnapshot;

import java.time.LocalDateTime;

public interface GoodsSnapshotDomainService {
    void saveSnapshot(GoodsSnapshot snapshot);

    long queryNewestSnapshotId(long goodsId, LocalDateTime updateTime);
}
