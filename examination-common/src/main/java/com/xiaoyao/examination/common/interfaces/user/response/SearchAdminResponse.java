package com.xiaoyao.examination.common.interfaces.user.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchAdminResponse implements Serializable {
    private long total;
    private List<Admin> results;

    @Data
    public static class Admin implements Serializable {
        private String id;
        private String username;
        private String name;
        private String photo;
        private String createTime;
    }
}
