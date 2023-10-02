package com.xiaoyao.examination.goods.domain.service.impl;

import com.xiaoyao.examination.goods.domain.entity.GoodsSnapshot;
import com.xiaoyao.examination.goods.domain.repository.GoodsSnapshotRepository;
import com.xiaoyao.examination.goods.domain.service.GoodsSnapshotDomainService;
import org.springframework.stereotype.Service;

@Service
public class GoodsSnapshotDomainServiceImpl implements GoodsSnapshotDomainService {
    private final GoodsSnapshotRepository goodsSnapshotRepository;

    public GoodsSnapshotDomainServiceImpl(GoodsSnapshotRepository goodsSnapshotRepository) {
        this.goodsSnapshotRepository = goodsSnapshotRepository;
    }

    @Override
    public void saveSnapshot(GoodsSnapshot snapshot) {
        goodsSnapshotRepository.save(snapshot);
    }

    @Override
    public String getSnapshotMd5(long goodsId) {
        return goodsSnapshotRepository.getSnapshotMd5(goodsId);
    }
}
