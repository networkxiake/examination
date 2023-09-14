package com.xiaoyao.examination.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    String getPathUrl(String path);

    String getDefaultPhotoPath();

    String uploadFile(MultipartFile file, String prefix);

    String getPhotoPrefix();

    void deleteFile(String path);

    void deleteFile(List<String> paths);
}
