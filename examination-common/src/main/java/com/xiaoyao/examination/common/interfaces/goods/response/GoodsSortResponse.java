package com.xiaoyao.examination.common.interfaces.goods.response;

import java.io.Serializable;
import java.util.List;

public class GoodsSortResponse implements Serializable {
    List<Sort> sorts;

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public static class Sort implements Serializable {
        private int sort;
        private String name;

        public Sort(Integer key, String value) {
            this.sort = key;
            this.name = value;
        }

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
