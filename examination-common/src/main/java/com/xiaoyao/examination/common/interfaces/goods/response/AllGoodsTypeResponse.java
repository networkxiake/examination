package com.xiaoyao.examination.common.interfaces.goods.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AllGoodsTypeResponse implements Serializable {
    private List<Type> types;

    @Data
    public static class Type implements Serializable {
        private int type;
        private String name;

        public Type(int type, String name) {
            this.type = type;
            this.name = name;
        }
    }
}
