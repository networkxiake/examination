package com.xiaoyao.examination.domain.service.impl;

import com.xiaoyao.examination.domain.repository.GoodsSnapshotRepository;
import com.xiaoyao.examination.domain.service.GoodsSnapshotDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsSnapshotDomainServiceImpl implements GoodsSnapshotDomainService {
    private final GoodsSnapshotRepository goodsSnapshotRepository;
}