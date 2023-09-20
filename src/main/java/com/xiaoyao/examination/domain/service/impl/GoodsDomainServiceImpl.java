package com.xiaoyao.examination.domain.service.impl;

import com.xiaoyao.examination.domain.entity.Goods;
import com.xiaoyao.examination.domain.enums.GoodsSort;
import com.xiaoyao.examination.domain.enums.GoodsStatus;
import com.xiaoyao.examination.domain.enums.GoodsType;
import com.xiaoyao.examination.domain.repository.GoodsRepository;
import com.xiaoyao.examination.domain.service.GoodsDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoodsDomainServiceImpl implements GoodsDomainService {
    private final GoodsRepository goodsRepository;

    @Override
    public void createGoods(Goods goods) {
        // 判断套餐类别是否存在
        checkGoodsType(goods.getType());
        // 判断套餐分类是否存在
        checkGoodsSort(goods.getSort());

        // 判断套餐名称或编号是否已存在
        if (goodsRepository.countGoodsByNameOrCode(goods.getName(), goods.getCode()) == 1) {
            throw new ExaminationException(ErrorCode.GOODS_NAME_OR_CODE_EXIST);
        }

        goods.setSalesVolume(0);
        goods.setStatus(GoodsStatus.ON.getStatus());
        goods.setUpdateTime(LocalDateTime.now());
        goods.setCreateTime(LocalDateTime.now());
        goodsRepository.insert(goods);
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
        // 判断状态是否存在
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

        Goods goods = new Goods();
        goods.setId(id);
        goods.setStatus(status);
        goods.setUpdateTime(LocalDateTime.now());
        goodsRepository.update(goods);
    }

    @Override
    public List<Goods> searchGoods(long page, long size, String code, String name, Integer type, Integer status, long[] total) {
        return goodsRepository.searchGoods(page, size, code, name, type, status, total);
    }

    @Override
    public Goods queryGoodsById(long id) {
        return goodsRepository.queryGoodsById(id);
    }

    @Override
    public void updateGoods(Goods goods) {
        // 判断套餐类别是否存在
        checkGoodsType(goods.getType());
        // 判断套餐分类是否存在
        checkGoodsSort(goods.getSort());

        // 判断套餐名称或编号是否已存在
        if (goods.getName() != null || goods.getCode() != null) {
            if (goodsRepository.countGoodsByNameOrCode(goods.getName(), goods.getCode()) == 1) {
                throw new ExaminationException(ErrorCode.GOODS_NAME_OR_CODE_EXIST);
            }
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

    @Override
    public Goods getUpdateGoodsById(long id) {
        return goodsRepository.getUpdateGoodsById(id);
    }

    @Override
    public void deleteGoods(List<Long> ids) {
        // 只有销量为零和已下架的套餐才能删除
        if (goodsRepository.countDontDeletedGoods(ids) > 0) {
            throw new ExaminationException(ErrorCode.GOODS_CAN_NOT_DELETE);
        }
        goodsRepository.deleteGoods(ids);
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
        return goodsRepository.getGoodsStatusById(id);
    }

    @Override
    public Map<Integer, String> getAllGoodsSort() {
        Map<Integer, String> sorts = new HashMap<>();
        for (GoodsStatus value : GoodsStatus.values()) {
            sorts.put(value.getStatus(), value.getName());
        }
        return sorts;
    }
}
