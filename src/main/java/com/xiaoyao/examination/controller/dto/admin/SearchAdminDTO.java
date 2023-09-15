package com.xiaoyao.examination.controller.dto.admin;

import lombok.Data;

import java.util.List;

@Data
public class SearchAdminDTO {
    private long total;
    private List<Admin> results;

    @Data
    public static class Admin {
        private String id;
        private String username;
        private String name;
        private String createTime;
    }
}
