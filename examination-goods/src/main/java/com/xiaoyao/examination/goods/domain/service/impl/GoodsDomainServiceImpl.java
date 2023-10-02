package com.xiaoyao.examination.goods.domain.service.impl;

import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.goods.domain.entity.Goods;
import com.xiaoyao.examination.goods.domain.enums.GoodsSort;
import com.xiaoyao.examination.goods.domain.enums.GoodsStatus;
import com.xiaoyao.examination.goods.domain.enums.GoodsType;
import com.xiaoyao.examination.goods.domain.repository.GoodsRepository;
import com.xiaoyao.examination.goods.domain.service.GoodsDomainService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsDomainServiceImpl implements GoodsDomainService {
    private final GoodsRepository goodsRepository;

    public GoodsDomainServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public long createGoods(Goods goods) {
        checkGoodsType(goods.getType());
        checkGoodsSort(goods.getSort());
        if (goodsRepository.isExistCode(goods.getCode(), null)) {
            throw new ExaminationException(ErrorCode.GOODS_CODE_EXIST);
        }

        goods.setUpdateTime(LocalDateTime.now());
        goods.setCreateTime(LocalDateTime.now());
        goodsRepository.save(goods);
        return goods.getId();
    }

    @Override
    public void updateSnapshotId(long goodsId, Long snapshotId) {
        goodsRepository.updateSnapshotId(goodsId, snapshotId);
    }

    @Override
    public Map<Integer, String> getAllGoodsType() {
        Map<Integer, String> types = new HashMap<>();
        for (GoodsType value : GoodsType.values()) {
            types.put(value.getType(), value.getName());
        }
        return types;
    }

    @Override
    public String getGoodsTypeById(int id) {
        for (GoodsType value : GoodsType.values()) {
            if (value.getType() == id) {
                return value.getName();
            }
        }
        throw new ExaminationException(ErrorCode.GOODS_TYPE_NOT_EXIST);
    }

    @Override
    public void changeStatus(long id, int status) {
        checkGoodsStatus(status);
        goodsRepository.updateStatus(id, status);
    }

    @Override
    public List<Goods> searchGoodsByAdmin(long page, long size,
                                          String code, String name, Integer type, Integer status, Integer sort,
                                          long[] total) {
        Goods goods = new Goods();
        goods.setCode(code);
        goods.setName(name);
        goods.setType(type);
        goods.setStatus(status);
        goods.setSort(sort);
        return goodsRepository.findGoodsListForAdminSearch(page, size, total, goods);
    }

    @Override
    public Goods queryGoodsById(long id) {
        return goodsRepository.findGoodsForUpdate(id);
    }

    @Override
    public void updateGoods(Goods goods) {
        checkGoodsType(goods.getType());
        checkGoodsSort(goods.getSort());
        if (goods.getCode() != null && goodsRepository.isExistCode(goods.getCode(), goods.getId())) {
            throw new ExaminationException(ErrorCode.GOODS_CODE_EXIST);
        }

        goods.setUpdateTime(LocalDateTime.now());
        goodsRepository.update(goods);
    }

    private void checkGoodsType(Integer type) {
        if (type != null) {
            boolean isTypeExist = false;
            for (GoodsType goodsType : GoodsType.values()) {
                if (goodsType.getType() == type) {
                    isTypeExist = true;
                    break;
                }
            }
            if (!isTypeExist) {
                throw new ExaminationException(ErrorCode.GOODS_TYPE_NOT_EXIST);
            }
        }
    }

    private void checkGoodsSort(Integer sort) {
        if (sort != null) {
            boolean isSortExist = false;
            for (GoodsSort goodsSort : GoodsSort.values()) {
                if (goodsSort.getSort() == sort) {
                    isSortExist = true;
                    break;
                }
            }
            if (!isSortExist) {
                throw new ExaminationException(ErrorCode.GOODS_SORT_NOT_EXIST);
            }
        }
    }

    private void checkGoodsStatus(Integer status) {
        if (status != null) {
            boolean isStatusExist = false;
            for (GoodsStatus goodsStatus : GoodsStatus.values()) {
                if (goodsStatus.getStatus() == status) {
                    isStatusExist = true;
                    break;
                }
            }
            if (!isStatusExist) {
                throw new ExaminationException(ErrorCode.GOODS_STATUS_NOT_EXIST);
            }
        }
    }

    @Override
    public Goods getUpdateGoodsById(long id) {
        return goodsRepository.findGoodsForPreUpdate(id);
    }

    @Override
    public Goods findGoodsForSubmitOrder(long id) {
        return goodsRepository.findGoodsForSubmitOrder(id);
    }

    @Override
    public void deleteGoods(List<Long> ids) {

        goodsRepository.delete(ids);
    }

    @Override
    public void changeFormItem(long id, String formItem) {
        Goods goods = new Goods();
        goods.setId(id);
        goods.setFormItem(formItem);
        goods.setUpdateTime(LocalDateTime.now());
        goodsRepository.update(goods);
    }

    @Override
    public int getGoodsStatusById(long id) {
        return goodsRepository.getGoodsStatus(id);
    }

    @Override
    public Map<Integer, String> getAllGoodsSort() {
        Map<Integer, String> sorts = new HashMap<>();
        for (GoodsSort value : GoodsSort.values()) {
            sorts.put(value.getSort(), value.getName());
        }
        return sorts;
    }

    @Override
    public List<Goods> getRecommendGoods(int sort, int count) {
        checkGoodsSort(sort);
        return goodsRepository.findGoodsListForUserRecommend(sort, count);
    }

    @Override
    public List<Goods> searchGoods(int pass, int size, String name, Integer type, String gender,
                                   String bottomPrice, String topPrice, String order) {
        checkGoodsType(type);
        return goodsRepository.findGoodsListForUserSearch(pass, size, name, type, gender, bottomPrice, topPrice, order);
    }

    @Override
    public Goods getSnapshotGoodsById(long goodsId) {
        return goodsRepository.findGoodsForSnapshot(goodsId);
    }

    @Override
    public Map<Long, Long> countGoodsByDiscountIds(List<Long> discountIds) {
        return goodsRepository.getGoodsCountInDiscountIds(discountIds);
    }

    @Override
    public void increaseSales(long goodsId, int count) {
        goodsRepository.updateSalesVolume(goodsId, count);
    }

    @Override
    public long countDontDeletedGoods(List<Long> ids) {
        return goodsRepository.countGoodsInIdsAndStatus(ids, GoodsStatus.ON.getStatus());
    }
}
