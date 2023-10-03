package com.xiaoyao.examination.api.controller.dto.user;

public class GenerateImageCodeDTO {
    private String key;
    private String imageBase64;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
