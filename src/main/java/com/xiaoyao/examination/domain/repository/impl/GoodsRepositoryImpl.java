package com.xiaoyao.examination.domain.repository.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyao.examination.domain.entity.Goods;
import com.xiaoyao.examination.domain.enums.GoodsStatus;
import com.xiaoyao.examination.domain.mapper.GoodsMapper;
import com.xiaoyao.examination.domain.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;
import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaUpdate;

@Repository
@RequiredArgsConstructor
public class GoodsRepositoryImpl implements GoodsRepository {
    private final GoodsMapper goodsMapper;

    @Override
    public void insert(Goods goods) {
        goodsMapper.insert(goods);
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
                .set(Goods::getOtherCheckup, temp.getOtherCheckup()));
    }

    @Override
    public List<Goods> searchGoods(long page, long size,
                                   String code, String name, Integer type, Integer status, Integer sort,
                                   long[] total) {
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
                .eq(code != null, Goods::getCode, code)
                .eq(name != null, Goods::getName, name)
                .eq(type != null, Goods::getType, type)
                .eq(status != null, Goods::getStatus, status)
                .eq(sort != null, Goods::getSort, sort));

        total[0] = goodsPage.getTotal();
        return goodsPage.getRecords();
    }

    @Override
    public long countGoodsByNameOrCode(String name, String code) {
        return goodsMapper.selectCount(lambdaQuery(Goods.class)
                .eq(Goods::getName, name)
                .or()
                .eq(Goods::getCode, code));
    }

    @Override
    public Goods queryGoodsById(long id) {
        return goodsMapper.selectOne(lambdaQuery(Goods.class)
                .select(Goods::getName,
                        Goods::getCode,
                        Goods::getDescription,
                        Goods::getOriginalPrice,
                        Goods::getCurrentPrice,
                        Goods::getDiscountId,
                        Goods::getImage,
                        Goods::getType,
                        Goods::getTag,
                        Goods::getDepartmentCheckup,
                        Goods::getLaboratoryCheckup,
                        Goods::getMedicalCheckup,
                        Goods::getOtherCheckup)
                .eq(Goods::getId, id));
    }

    @Override
    public Goods getUpdateGoodsById(long id) {
        return goodsMapper.selectOne(lambdaQuery(Goods.class)
                .select(Goods::getImage,
                        Goods::getStatus)
                .eq(Goods::getId, id));
    }

    @Override
    public void deleteGoods(List<Long> ids) {
        goodsMapper.deleteBatchIds(ids);
    }

    @Override
    public long countDontDeletedGoods(List<Long> ids) {
        return goodsMapper.selectCount(lambdaQuery(Goods.class)
                .in(Goods::getId, ids)
                .and(i -> i.eq(Goods::getStatus, GoodsStatus.ON.getStatus())
                        .or()
                        .ne(Goods::getSalesVolume, 0)));
    }

    @Override
    public int getGoodsStatusById(long id) {
        return goodsMapper.selectOne(lambdaQuery(Goods.class)
                .select(Goods::getStatus)
                .eq(Goods::getId, id)).getStatus();
    }

    @Override
    public List<Goods> getRecommendGoods(int sort, int count) {
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
    public List<Goods> searchGoodsByPage(int pass, int size, String name, Integer type, String gender,
                                         String bottomPrice, String topPrice, String order) {
        return goodsMapper.searchGoodsByPage(pass, size, name, type, gender, bottomPrice, topPrice, order);
    }
}
