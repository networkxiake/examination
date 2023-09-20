package com.xiaoyao.examination.controller.dto.goods;

import lombok.Data;

import java.util.List;

@Data
public class GoodsSortDTO {
    List<Sort> sorts;

    @Data
    public static class Sort {
        private int sort;
        private String name;

        public Sort(Integer key, String value) {
            this.sort = key;
            this.name = value;
        }
    }
}
