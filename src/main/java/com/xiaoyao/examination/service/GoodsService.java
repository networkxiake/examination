package com.xiaoyao.examination.service;

import com.xiaoyao.examination.controller.dto.goods.*;
import com.xiaoyao.examination.controller.form.goods.CreateForm;
import com.xiaoyao.examination.controller.form.goods.AdminSearchForm;
import com.xiaoyao.examination.controller.form.goods.SearchGoodsForm;
import com.xiaoyao.examination.controller.form.goods.UpdateForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface GoodsService {
    void createGoods(CreateForm form);

    GoodsTypeDTO getAllGoodsType();

    void changeStatus(long id, int status);

    AdminSearchGoodsDTO searchGoodsByAdmin(AdminSearchForm form);

    QueryGoodsDTO queryGoods(long id);

    void updateGoods(UpdateForm form);

    void deleteGoods(List<Long> ids);

    /**
     * 上传指定体检套餐的Excel文件，如果已存在则覆盖。
     */
    void uploadExcel(long goodsId, MultipartFile file);

    /**
     * 获取指定体检套餐的Excel文件的URL。
     */
    String getExcelUrl(long goodsId);

    GoodsSortDTO sort();

    SearchGoodsDTO recommend(int sort, int count);

    SearchGoodsDTO searchGoods(SearchGoodsForm form);

    Map<Long, Long> countGoodsByDiscountIds(List<Long> discountIds);
}
