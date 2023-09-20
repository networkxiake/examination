package com.xiaoyao.examination.service;

import com.xiaoyao.examination.controller.dto.goods.GoodsSortDTO;
import com.xiaoyao.examination.controller.dto.goods.GoodsTypeDTO;
import com.xiaoyao.examination.controller.dto.goods.QueryGoodsDTO;
import com.xiaoyao.examination.controller.dto.goods.SearchGoodsDTO;
import com.xiaoyao.examination.controller.form.goods.CreateForm;
import com.xiaoyao.examination.controller.form.goods.SearchForm;
import com.xiaoyao.examination.controller.form.goods.UpdateForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoodsService {
    void createGoods(CreateForm form);

    GoodsTypeDTO getAllGoodsType();

    void changeStatus(long id, int status);

    SearchGoodsDTO searchGoods(SearchForm form);

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
}
