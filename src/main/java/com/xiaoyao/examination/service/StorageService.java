package com.xiaoyao.examination.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String getPathUrl(String path);

    String getDefaultPhotoPath();

    String uploadFile(MultipartFile file, String prefix);
}
