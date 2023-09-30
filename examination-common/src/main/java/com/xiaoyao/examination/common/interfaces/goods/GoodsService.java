package com.xiaoyao.examination.common.interfaces.goods;

import com.xiaoyao.examination.common.interfaces.goods.request.AdminSearchRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.CreateGoodsRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.SearchGoodsRequest;
import com.xiaoyao.examination.common.interfaces.goods.request.UpdateGoodsRequest;
import com.xiaoyao.examination.common.interfaces.goods.response.*;

import java.util.List;
import java.util.Map;

public interface GoodsService {
    void createGoods(CreateGoodsRequest request);

    ApplyUploadFileResponse applyUploadPhoto(String suffix);

    AllGoodsTypeResponse getAllGoodsType();

    void changeStatus(long id, int status);

    AdminSearchGoodsResponse searchGoodsByAdmin(AdminSearchRequest request);

    QueryGoodsResponse queryGoods(long id);

    void updateGoods(UpdateGoodsRequest request);

    void deleteGoods(List<Long> ids);

    ApplyUploadFileResponse applyUploadExcel(long goodsId, String suffix);

    void updateExcel(long goodsId, String path);

    /**
     * 获取指定体检套餐的Excel文件的URL。
     */
    String getExcelUrl(long goodsId);

    GoodsSortResponse sort();

    SearchGoodsResponse recommend(int sort, int count);

    SearchGoodsResponse searchGoods(SearchGoodsRequest request);

    Map<Long, Long> countGoodsByDiscountIds(List<Long> discountIds);

    /**
     * 查询下单时的套餐信息。
     */
    SubmitOrderGoodsInfoResponse getGoodsInfoInSubmitOrder(long goodsId);
}
