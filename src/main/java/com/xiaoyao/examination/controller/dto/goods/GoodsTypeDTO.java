package com.xiaoyao.examination.controller.dto.goods;

import lombok.Data;

import java.util.List;

@Data
public class GoodsTypeDTO {
    private List<Type> types;

    @Data
    public static class Type {
        private int type;
        private String name;

        public Type(int type, String name) {
            this.type = type;
            this.name = name;
        }
    }
}
