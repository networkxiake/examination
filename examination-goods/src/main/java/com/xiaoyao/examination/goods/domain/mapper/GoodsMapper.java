package com.xiaoyao.examination.goods.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyao.examination.goods.domain.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    List<Goods> searchGoodsByPage(@Param("pass") int pass,
                                  @Param("size") int size,
                                  @Param("name") String name,
                                  @Param("type") Integer type,
                                  @Param("gender") String gender,
                                  @Param("bottomPrice") String bottomPrice,
                                  @Param("topPrice") String topPrice,
                                  @Param("order") String order);

    List<Map<String, Object>> countGoodsByDiscountIds(List<Long> discountIds);
}




