package com.xiaoyao.examination.goods.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.common.interfaces.goods.GoodsService;
import com.xiaoyao.examination.common.interfaces.goods.request.AdminSearchRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.CreateGoodsRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.SearchGoodsRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.UpdateGoodsRequest;
import com.xiaoyao.examination.common.interfaces.goods.response.*;
import com.xiaoyao.examination.common.interfaces.storage.StorageService;
import com.xiaoyao.examination.goods.domain.entity.Goods;
import com.xiaoyao.examination.goods.domain.entity.GoodsSnapshot;
import com.xiaoyao.examination.goods.domain.enums.GoodsStatus;
import com.xiaoyao.examination.goods.domain.service.DiscountDomainService;
import com.xiaoyao.examination.goods.domain.service.GoodsDomainService;
import com.xiaoyao.examination.goods.domain.service.GoodsSnapshotDomainService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@DubboService
public class GoodsServiceImpl implements GoodsService {
    private final GoodsDomainService goodsDomainService;
    private final DiscountDomainService discountDomainService;
    private final GoodsSnapshotDomainService snapshotDomainService;

    @DubboReference
    private StorageService storageService;

    public GoodsServiceImpl(GoodsDomainService goodsDomainService, DiscountDomainService discountDomainService,
                            GoodsSnapshotDomainService snapshotDomainService) {
        this.goodsDomainService = goodsDomainService;
        this.discountDomainService = discountDomainService;
        this.snapshotDomainService = snapshotDomainService;
    }

    @Transactional
    @Override
    public void createGoods(CreateGoodsRequest request) {
        checkDiscountId(request.getDiscountId());

        Goods goods = BeanUtil.copyProperties(request, Goods.class);
        goods.setSalesVolume(0);
        goods.setStatus(GoodsStatus.OFF.getStatus());
        long goodsId = goodsDomainService.createGoods(goods);

        storageService.referenceSpace(request.getImage());
        updateSnapshot(goodsId);
    }

    @Override
    public ApplyUploadFileResponse applyUploadPhoto(String suffix) {
        List<String> strings = storageService.applySpaceForImage(IdUtil.fastSimpleUUID() + "." + suffix);
        ApplyUploadFileResponse response = new ApplyUploadFileResponse();
        response.setPath(strings.get(0));
        response.setUrl(strings.get(1));
        return response;
    }

    private void checkDiscountId(Long discountId) {
        if (discountId != null && !discountDomainService.isIdExist(discountId)) {
            throw new ExaminationException(ErrorCode.DISCOUNT_NOT_EXIST);
        }
    }

    private void updateSnapshot(long goodsId) {
        Goods goods = goodsDomainService.getSnapshotGoodsById(goodsId);

        // 计算套餐的md5值
        GoodsSnapshot snapshot = new GoodsSnapshot();
        snapshot.setName(goods.getName());
        snapshot.setDescription(goods.getDescription());
        snapshot.setImage(goods.getImage());
        snapshot.setOriginalPrice(goods.getOriginalPrice());
        snapshot.setCurrentPrice(goods.getCurrentPrice());
        snapshot.setType(goods.getType());
        snapshot.setTag(goods.getTag());
        snapshot.setDepartmentCheckup(goods.getDepartmentCheckup());
        snapshot.setLaboratoryCheckup(goods.getLaboratoryCheckup());
        snapshot.setMedicalCheckup(goods.getMedicalCheckup());
        snapshot.setOtherCheckup(goods.getOtherCheckup());
        String md5 = DigestUtil.md5Hex(JSONUtil.toJsonStr(snapshot));

        if (goods.getSnapshotId() != null && snapshotDomainService.getSnapshotMd5(goods.getSnapshotId()).equals(md5)) {
            // md5值没有发生变化，不需要更新快照。
            return;
        }

        snapshot.setSnapshotMd5(md5);
        snapshotDomainService.saveSnapshot(snapshot);

        goodsDomainService.updateSnapshotId(goodsId, snapshot.getId());
    }

    @Override
    public AllGoodsTypeResponse getAllGoodsType() {
        List<AllGoodsTypeResponse.Type> types = new ArrayList<>();
        goodsDomainService.getAllGoodsType().forEach((key, value) ->
                types.add(new AllGoodsTypeResponse.Type(key, value)));

        AllGoodsTypeResponse response = new AllGoodsTypeResponse();
        response.setTypes(types);
        return response;
    }

    @Override
    public void changeStatus(long id, int status) {
        goodsDomainService.changeStatus(id, status);
    }

    @Override
    public AdminSearchGoodsResponse searchGoodsByAdmin(AdminSearchRequest request) {
        long[] total = new long[1];
        List<AdminSearchGoodsResponse.Goods> result = new ArrayList<>();

        Map<Integer, String> goodsType = goodsDomainService.getAllGoodsType();
        goodsDomainService.searchGoodsByAdmin(request.getPage(), request.getSize(),
                request.getCode(), request.getName(), request.getType(), request.getStatus(), request.getSort(), total).forEach(item -> {
            AdminSearchGoodsResponse.Goods goods = new AdminSearchGoodsResponse.Goods();
            goods.setId(String.valueOf(item.getId()));
            goods.setName(item.getName());
            goods.setCode(item.getCode());
            goods.setOriginalPrice(item.getOriginalPrice().toString());
            goods.setCurrentPrice(item.getCurrentPrice().toString());
            goods.setDiscountId(item.getDiscountId() != null ? String.valueOf(item.getDiscountId()) : null);
            goods.setSalesVolume(item.getSalesVolume());
            goods.setType(goodsType.get(item.getType()));
            goods.setStatus(item.getStatus());
            goods.setHasExcel(item.getFormItem() != null);
            result.add(goods);
        });

        AdminSearchGoodsResponse response = new AdminSearchGoodsResponse();
        response.setTotal(total[0]);
        response.setGoodsList(result);
        return response;
    }

    @Override
    public QueryGoodsResponse queryGoods(long id) {
        Goods goods = goodsDomainService.queryGoodsById(id);
        if (goods == null) {
            throw new ExaminationException(ErrorCode.GOODS_NOT_EXIST);
        }

        QueryGoodsResponse response = new QueryGoodsResponse();
        response.setName(goods.getName());
        response.setCode(goods.getCode());
        response.setDescription(goods.getDescription());
        response.setOriginalPrice(goods.getOriginalPrice().toString());
        response.setCurrentPrice(goods.getCurrentPrice().toString());
        response.setDiscountId(goods.getDiscountId() != null ? String.valueOf(goods.getDiscountId()) : null);
        response.setImage(storageService.getPathDownloadingUrl(goods.getImage()));
        response.setType(goods.getType());
        response.setSort(goods.getSort());
        response.setTag(JSONUtil.parseArray(goods.getTag()).toList(String.class));
        if (goods.getDepartmentCheckup() != null) {
            response.setDepartmentCheckup(JSONUtil.parseArray(goods.getDepartmentCheckup()).toList(QueryGoodsResponse.Item.class));
        }
        if (goods.getLaboratoryCheckup() != null) {
            response.setLaboratoryCheckup(JSONUtil.parseArray(goods.getLaboratoryCheckup()).toList(QueryGoodsResponse.Item.class));
        }
        if (goods.getMedicalCheckup() != null) {
            response.setMedicalCheckup(JSONUtil.parseArray(goods.getMedicalCheckup()).toList(QueryGoodsResponse.Item.class));
        }
        if (goods.getOtherCheckup() != null) {
            response.setOtherCheckup(JSONUtil.parseArray(goods.getOtherCheckup()).toList(QueryGoodsResponse.Item.class));
        }
        return response;
    }

    @Override
    public void updateGoods(UpdateGoodsRequest request) {
        checkDiscountId(request.getDiscountId());

        Goods rawGoods = goodsDomainService.getUpdateGoodsById(request.getId());
        // 套餐已下架才可以修改
        if (rawGoods.getStatus() == GoodsStatus.ON.getStatus()) {
            throw new ExaminationException(ErrorCode.GOODS_STATUS_NOT_ALLOW_UPDATE);
        }

        Goods goods = BeanUtil.copyProperties(request, Goods.class);
        goodsDomainService.updateGoods(goods);

        updateSnapshot(goods.getId());
        if (request.getImage() != null) {
            storageService.referenceSpace(request.getImage());
        }
    }

    @Override
    public void deleteGoods(List<Long> ids) {
        // 只有已下架的套餐才能删除
        if (goodsDomainService.countDontDeletedGoods(ids) > 0) {
            throw new ExaminationException(ErrorCode.GOODS_CAN_NOT_DELETE);
        }
        goodsDomainService.deleteGoods(ids);
        List<String> paths = new ArrayList<>();
        ids.forEach(id -> paths.add(id + ".xlsx"));
        storageService.releaseSpace(paths);
    }

    @Override
    public ApplyUploadFileResponse applyUploadExcel(long goodsId, String suffix) {
        if (!suffix.equals("xlsx")) {
            throw new ExaminationException(ErrorCode.INVALID_PARAMS);
        }
        List<String> strings = storageService.applySpaceForExcel(goodsId + "." + suffix);
        ApplyUploadFileResponse response = new ApplyUploadFileResponse();
        response.setPath(strings.get(0));
        response.setUrl(strings.get(1));
        return response;
    }

    @Override
    public void updateExcel(long goodsId, String path) {
        // 套餐已下架才可以修改
        if (goodsDomainService.getGoodsStatusById(goodsId) == GoodsStatus.ON.getStatus()) {
            throw new ExaminationException(ErrorCode.GOODS_STATUS_NOT_ALLOW_UPDATE);
        }
        goodsDomainService.changeFormItem(goodsId, parseExcel(path));
    }

    private String parseExcel(String path) {
        // TODO 解析excel
        return "[1,2,3]";
    }

    @Override
    public String getExcelUrl(long goodsId) {
        return storageService.getExcelDownloadingUrl(goodsId + ".xlsx");
    }

    @Override
    public GoodsSortResponse sort() {
        List<GoodsSortResponse.Sort> sorts = new ArrayList<>();
        goodsDomainService.getAllGoodsSort().forEach((key, value) ->
                sorts.add(new GoodsSortResponse.Sort(key, value)));

        GoodsSortResponse response = new GoodsSortResponse();
        response.setSorts(sorts);
        return response;
    }

    @Override
    public SearchGoodsResponse recommend(int sort, int count) {
        // TODO 添加缓存
        List<SearchGoodsResponse.Goods> goodsList = new ArrayList<>();
        goodsDomainService.getRecommendGoods(sort, count).forEach(item -> {
            SearchGoodsResponse.Goods goods = new SearchGoodsResponse.Goods();
            goods.setId(String.valueOf(item.getId()));
            goods.setName(item.getName());
            goods.setDescription(item.getDescription());
            goods.setImage(storageService.getPathDownloadingUrl(item.getImage()));
            goods.setOriginalPrice(item.getOriginalPrice().toString());
            goods.setCurrentPrice(item.getCurrentPrice().toString());
            goods.setSalesVolume(item.getSalesVolume());
            goods.setDiscountId(item.getDiscountId() != null ? String.valueOf(item.getDiscountId()) : null);
            goodsList.add(goods);
        });

        SearchGoodsResponse response = new SearchGoodsResponse();
        response.setGoodsList(goodsList);
        return response;
    }

    @Override
    public SearchGoodsResponse searchGoods(SearchGoodsRequest request) {
        List<SearchGoodsResponse.Goods> goodsList = new ArrayList<>();
        goodsDomainService.searchGoods(request.getPass(), request.getSize(), request.getName(), request.getType(),
                request.getGender(), request.getBottomPrice(), request.getTopPrice(), request.getOrder()).forEach(item -> {
            SearchGoodsResponse.Goods goods = new SearchGoodsResponse.Goods();
            goods.setId(String.valueOf(item.getId()));
            goods.setName(item.getName());
            goods.setDescription(item.getDescription());
            goods.setImage(storageService.getPathDownloadingUrl(item.getImage()));
            goods.setOriginalPrice(item.getOriginalPrice().toString());
            goods.setCurrentPrice(item.getCurrentPrice().toString());
            goods.setSalesVolume(item.getSalesVolume());
            goods.setDiscountId(item.getDiscountId() != null ? String.valueOf(item.getDiscountId()) : null);
            goodsList.add(goods);
        });

        SearchGoodsResponse dto = new SearchGoodsResponse();
        dto.setGoodsList(goodsList);
        return dto;
    }

    @Override
    public Map<Long, Long> countGoodsByDiscountIds(List<Long> discountIds) {
        return goodsDomainService.countGoodsByDiscountIds(discountIds);
    }

    @Override
    public SubmitOrderGoodsInfoResponse getGoodsInfoInSubmitOrder(long goodsId) {
        Goods goods = goodsDomainService.findGoodsForSubmitOrder(goodsId);
        SubmitOrderGoodsInfoResponse response = BeanUtil.copyProperties(goods, SubmitOrderGoodsInfoResponse.class);
        response.setUpStatus(goods.getStatus() == GoodsStatus.ON.getStatus());
        return response;
    }

    @Override
    public void increaseSalesVolume(long goodsId, int count) {
        goodsDomainService.increaseSales(goodsId, count);
    }

    @Override
    public void decreaseSalesVolume(long goodsId, int count) {
        goodsDomainService.decreaseSales(goodsId, count);
    }
}
