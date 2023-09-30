package com.xiaoyao.examination.common.interfaces.goods.response;

import java.io.Serializable;

public class ApplyUploadFileResponse implements Serializable {
    private String path;
    private String url;

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
