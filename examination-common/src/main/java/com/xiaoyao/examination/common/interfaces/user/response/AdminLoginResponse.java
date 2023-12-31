package com.xiaoyao.examination.common.interfaces.user.response;

import java.io.Serializable;

public class AdminLoginResponse implements Serializable {
    private Long id;
    private boolean isInitAdmin;
    private String name;
    private String photo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isInitAdmin() {
        return isInitAdmin;
    }

    public void setInitAdmin(boolean initAdmin) {
        isInitAdmin = initAdmin;
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
}
