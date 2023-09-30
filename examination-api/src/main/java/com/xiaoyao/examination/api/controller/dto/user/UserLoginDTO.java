package com.xiaoyao.examination.api.controller.dto.user;

public class UserLoginDTO {
    private String token;
    private String name;
    private String photo;

    public UserLoginDTO(String token, String name, String photo) {
        this.token = token;
        this.name = name;
        this.photo = photo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
