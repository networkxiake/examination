package com.xiaoyao.examination.controller;

import com.xiaoyao.examination.response.ResponseBody;
import com.xiaoyao.examination.response.ResponseBodyBuilder;
import com.xiaoyao.examination.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传临时文件用的接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final StorageService storageService;

    @PostMapping("/upload-photo")
    public ResponseBody<String> uploadPhoto(MultipartFile file) {
        return ResponseBodyBuilder.build(storageService.uploadFile(file, storageService.getPhotoPrefix()));
    }
}
