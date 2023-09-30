package com.xiaoyao.examination.common.interfaces.user.response;

import java.io.Serializable;
import java.util.List;

public class SearchAdminResponse implements Serializable {
    private long total;
    private List<Admin> results;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Admin> getResults() {
        return results;
    }

    public void setResults(List<Admin> results) {
        this.results = results;
    }

    public static class Admin implements Serializable {
        private String id;
        private String username;
        private String name;
        private String photo;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
