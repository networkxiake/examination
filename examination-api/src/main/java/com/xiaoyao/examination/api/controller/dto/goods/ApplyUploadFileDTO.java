package com.xiaoyao.examination.api.controller.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplyUploadFileDTO {
    private String path;
    private String url;
}
