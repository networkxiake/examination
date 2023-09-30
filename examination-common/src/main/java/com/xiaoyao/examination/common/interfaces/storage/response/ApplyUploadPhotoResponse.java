package com.xiaoyao.examination.common.interfaces.storage.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApplyUploadPhotoResponse implements Serializable {
    private String path;
    private String url;
}
