package com.xiaoyao.examination.common.interfaces.goods.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class SearchGoodsRequest implements Serializable {
    private int pass;
    private int size;
    private String name;
    private Integer type;
    private String gender;
    private String bottomPrice;
    private String topPrice;
    private String order;
}
