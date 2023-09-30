package com.xiaoyao.examination.goods.domain.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyao.examination.goods.domain.entity.Goods;
import com.xiaoyao.examination.goods.domain.enums.GoodsStatus;
import com.xiaoyao.examination.goods.domain.mapper.GoodsMapper;
import com.xiaoyao.examination.goods.domain.repository.GoodsRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;
import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaUpdate;

@Repository
public class GoodsRepositoryImpl implements GoodsRepository {
    private final GoodsMapper goodsMapper;

    public GoodsRepositoryImpl(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public long countGoodsInIdsAndStatus(List<Long> ids, int status) {
        return goodsMapper.selectCount(lambdaQuery(Goods.class)
                .in(Goods::getId, ids)
                .eq(Goods::getStatus, status));
    }

    @Override
    public List<Goods> findGoodsListForAdminSearch(long page, long size, long[] total, Goods goods) {
        Page<Goods> goodsPage = goodsMapper.selectPage(Page.of(page, size), lambdaQuery(Goods.class)
                .select(Goods::getId,
                        Goods::getName,
                        Goods::getCode,
                        Goods::getOriginalPrice,
                        Goods::getCurrentPrice,
                        Goods::getDiscountId,
                        Goods::getSalesVolume,
                        Goods::getType,
                        Goods::getStatus,
                        Goods::getFormItem)
                .eq(StrUtil.isNotBlank(goods.getCode()), Goods::getCode, goods.getCode())
                .eq(StrUtil.isNotBlank(goods.getName()), Goods::getName, goods.getName())
                .eq(goods.getType() != null, Goods::getType, goods.getType())
                .eq(goods.getStatus() != null, Goods::getStatus, goods.getStatus())
                .eq(goods.getSort() != null, Goods::getSort, goods.getSort()));
        total[0] = goodsPage.getTotal();
        return goodsPage.getRecords();
    }

    @Override
    public List<Goods> findGoodsListForUserRecommend(int sort, int count) {
        return goodsMapper.selectList(lambdaQuery(Goods.class)
                .select(Goods::getId,
                        Goods::getName,
                        Goods::getDescription,
                        Goods::getImage,
                        Goods::getOriginalPrice,
                        Goods::getCurrentPrice,
                        Goods::getSalesVolume,
                        Goods::getDiscountId)
                .eq(Goods::getSort, sort)
                .eq(Goods::getStatus, GoodsStatus.ON.getStatus())
                .orderByDesc(Goods::getSalesVolume)
                .last("LIMIT " + count));
    }

    @Override
    public List<Goods> findGoodsListForUserSearch(int pass, int size, String name, Integer type, String gender,
                                                  String bottomPrice, String topPrice, String order) {
        return goodsMapper.searchGoodsByPage(pass, size, name, type, gender, bottomPrice, topPrice, order);
    }

    @Override
    public Goods findGoodsForUpdate(long id) {
        return goodsMapper.selectOne(lambdaQuery(Goods.class)
                .select(Goods::getName,
                        Goods::getCode,
                        Goods::getDescription,
                        Goods::getOriginalPrice,
                        Goods::getCurrentPrice,
                        Goods::getDiscountId,
                        Goods::getImage,
                        Goods::getType,
                        Goods::getSort,
                        Goods::getTag,
                        Goods::getDepartmentCheckup,
                        Goods::getLaboratoryCheckup,
                        Goods::getMedicalCheckup,
                        Goods::getOtherCheckup)
                .eq(Goods::getId, id));
    }

    @Override
    public Goods findGoodsForPreUpdate(long id) {
        return goodsMapper.selectOne(lambdaQuery(Goods.class)
                .select(Goods::getImage,
                        Goods::getStatus)
                .eq(Goods::getId, id));
    }

    @Override
    public Goods findGoodsForSnapshot(long id) {
        return goodsMapper.selectOne(lambdaQuery(Goods.class)
                .select(Goods::getName,
                        Goods::getDescription,
                        Goods::getImage,
                        Goods::getOriginalPrice,
                        Goods::getCurrentPrice,
                        Goods::getType,
                        Goods::getTag,
                        Goods::getDepartmentCheckup,
                        Goods::getLaboratoryCheckup,
                        Goods::getMedicalCheckup,
                        Goods::getOtherCheckup,
                        Goods::getSnapshotMd5)
                .eq(Goods::getId, id));
    }

    @Override
    public Goods findGoodsForSubmitOrder(long id) {
        return goodsMapper.selectOne(lambdaQuery(Goods.class)
                .select(Goods::getName,
                        Goods::getDescription,
                        Goods::getImage,
                        Goods::getCurrentPrice,
                        Goods::getDiscountId,
                        Goods::getUpdateTime)
                .eq(Goods::getId, id)
                .eq(Goods::getStatus, GoodsStatus.ON.getStatus()));
    }

    @Override
    public int getGoodsStatus(long id) {
        return goodsMapper.selectOne(lambdaQuery(Goods.class)
                .select(Goods::getStatus)
                .eq(Goods::getId, id)).getStatus();
    }

    @Override
    public Map<Long, Long> getGoodsCountInDiscountIds(List<Long> discountIds) {
        Map<Long, Long> result = new HashMap<>();
        goodsMapper.countGoodsByDiscountIds(discountIds).forEach(map ->
                result.put(((BigInteger) map.get("discount_id")).longValue(), (Long) map.get("count")));
        return result;
    }

    @Override
    public boolean isExistCode(String code, Long goodsId) {
        return goodsMapper.selectCount(lambdaQuery(Goods.class)
                .eq(Goods::getCode, code)
                .ne(goodsId != null, Goods::getId, goodsId)) > 0;
    }

    @Override
    public void save(Goods goods) {
        goodsMapper.insert(goods);
    }

    @Override
    public void delete(List<Long> ids) {
        goodsMapper.deleteBatchIds(ids);
    }

    @Override
    public void update(Goods goods) {
        // 区分值为null时的可选字段
        Goods temp = new Goods();
        temp.setDiscountId(goods.getDiscountId());
        goods.setDiscountId(null);
        temp.setTag(goods.getTag());
        goods.setTag(null);
        temp.setDepartmentCheckup(goods.getDepartmentCheckup());
        goods.setDepartmentCheckup(null);
        temp.setLaboratoryCheckup(goods.getLaboratoryCheckup());
        goods.setLaboratoryCheckup(null);
        temp.setMedicalCheckup(goods.getMedicalCheckup());
        goods.setMedicalCheckup(null);
        temp.setOtherCheckup(goods.getOtherCheckup());
        goods.setOtherCheckup(null);
        goodsMapper.update(goods, lambdaUpdate(Goods.class)
                .set(Goods::getDiscountId, temp.getDiscountId())
                .set(Goods::getTag, temp.getTag())
                .set(Goods::getDepartmentCheckup, temp.getDepartmentCheckup())
                .set(Goods::getLaboratoryCheckup, temp.getLaboratoryCheckup())
                .set(Goods::getMedicalCheckup, temp.getMedicalCheckup())
                .set(Goods::getOtherCheckup, temp.getOtherCheckup())
                .eq(Goods::getId, goods.getId()));
    }

    @Override
    public void updateSnapshot(long goodsId, String md5) {
        goodsMapper.update(null, lambdaUpdate(Goods.class)
                .set(Goods::getSnapshotMd5, md5)
                .eq(Goods::getId, goodsId));
    }

    @Override
    public void updateStatus(long id, int status) {
        goodsMapper.update(null, lambdaUpdate(Goods.class)
                .set(Goods::getStatus, status)
                .eq(Goods::getId, id));
    }

    @Override
    public void updateSalesVolume(long id, int count) {
        goodsMapper.update(null, lambdaUpdate(Goods.class)
                .setSql("sales_volume = sales_volume + " + count)
                .eq(Goods::getId, id));
    }
}
