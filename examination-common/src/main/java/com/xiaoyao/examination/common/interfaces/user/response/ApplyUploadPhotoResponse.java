package com.xiaoyao.examination.common.interfaces.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ApplyUploadPhotoResponse implements Serializable {
    private String path;
    private String url;
}
