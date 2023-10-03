package com.xiaoyao.examination.goods.domain.repository;

import com.xiaoyao.examination.goods.domain.entity.GoodsSnapshot;

public interface GoodsSnapshotRepository {
    String getSnapshotMd5(long id);

    void save(GoodsSnapshot snapshot);
}
