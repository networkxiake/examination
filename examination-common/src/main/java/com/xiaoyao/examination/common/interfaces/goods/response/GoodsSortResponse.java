package com.xiaoyao.examination.common.interfaces.goods.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsSortResponse implements Serializable {
    List<Sort> sorts;

    @Data
    public static class Sort implements Serializable {
        private int sort;
        private String name;

        public Sort(Integer key, String value) {
            this.sort = key;
            this.name = value;
        }
    }
}
