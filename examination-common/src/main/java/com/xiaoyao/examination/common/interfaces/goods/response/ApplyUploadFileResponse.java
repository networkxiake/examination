package com.xiaoyao.examination.common.interfaces.goods.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApplyUploadFileResponse implements Serializable {
    private String path;
    private String url;
}
