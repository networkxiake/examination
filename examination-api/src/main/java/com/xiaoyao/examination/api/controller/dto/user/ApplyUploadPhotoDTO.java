package com.xiaoyao.examination.api.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplyUploadPhotoDTO {
    private String path;
    private String url;
}
