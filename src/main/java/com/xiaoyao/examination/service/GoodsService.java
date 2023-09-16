package com.xiaoyao.examination.service;

import com.xiaoyao.examination.controller.dto.goods.GoodsTypeDTO;
import com.xiaoyao.examination.controller.dto.goods.QueryGoodsDTO;
import com.xiaoyao.examination.controller.dto.goods.SearchGoodsDTO;
import com.xiaoyao.examination.controller.form.goods.CreateForm;
import com.xiaoyao.examination.controller.form.goods.SearchForm;

public interface GoodsService {
    void createGoods(CreateForm form);

    GoodsTypeDTO getAllGoodsType();

    void changeStatus(long id, int status);

    SearchGoodsDTO searchGoods(SearchForm form);

    QueryGoodsDTO queryGoods(long id);
}
