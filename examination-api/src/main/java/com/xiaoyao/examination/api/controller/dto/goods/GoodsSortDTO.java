package com.xiaoyao.examination.api.controller.dto.goods;

import java.util.List;

public class GoodsSortDTO {
    List<Sort> sorts;

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public static class Sort {
        private int sort;
        private String name;

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}