package com.xiaoyao.examination.common.interfaces.user.response;

import java.io.Serializable;

public class ApplyUploadPhotoResponse implements Serializable {
    private String path;
    private String url;

    public ApplyUploadPhotoResponse(String path, String url) {
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
