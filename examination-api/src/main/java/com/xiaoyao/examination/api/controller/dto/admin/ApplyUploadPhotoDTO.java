package com.xiaoyao.examination.api.controller.dto.admin;

public class ApplyUploadPhotoDTO {
    private String path;
    private String url;

    public ApplyUploadPhotoDTO(String path, String url) {
        this.path = path;
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
