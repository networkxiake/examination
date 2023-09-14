package com.xiaoyao.examination.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.properties.MinIOProperties;
import com.xiaoyao.examination.service.StorageService;
import com.xiaoyao.examination.service.event.FileUploadedEvent;
import io.minio.*;
import io.minio.messages.DeleteObject;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final String DEFAULT_PHOTO = "default-photo.jpg";
    private final String PHOTO_PREFIX = "photo/";

    private final MinIOProperties minIOProperties;
    private final MinioClient minioClient;
    private final RedissonClient redissonClient;
    private final ApplicationEventMulticaster eventMulticaster;

    @PostConstruct
    public void init() throws Exception {
        if (isBucketNotExist()) {
            RLock lock = redissonClient.getLock("minio");
            if (lock.tryLock()) {
                try {
                    if (isBucketNotExist()) {
                        // 创建存储桶并设置访问权限为公共读
                        minioClient.makeBucket(MakeBucketArgs.builder()
                                .bucket(minIOProperties.getBucketName())
                                .build());
                        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                                .bucket(minIOProperties.getBucketName())
                                .config(getPublicOnlyPolicy(minIOProperties.getBucketName()))
                                .build());

                        // 上传用户默认头像
                        minioClient.putObject(PutObjectArgs.builder()
                                .bucket(minIOProperties.getBucketName())
                                .object(DEFAULT_PHOTO)
                                .stream(getClass().getClassLoader().getResourceAsStream("images/" + DEFAULT_PHOTO),
                                        -1, 5 * 1024 * 1024)
                                .contentType("image/jpeg")
                                .build());
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private boolean isBucketNotExist() throws Exception {
        return !minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minIOProperties.getBucketName())
                .build());
    }

    private String getPublicOnlyPolicy(String bucket) {
        return StrUtil.format("""
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Effect": "Allow",
                            "Principal": {"AWS": ["*"]},
                            "Action": ["s3:GetBucketLocation"],
                            "Resource": ["arn:aws:s3:::{}"]
                        },
                        {
                            "Effect": "Allow",
                            "Principal": {"AWS": ["*"]},
                            "Action": ["s3:GetObject"],
                            "Resource": ["arn:aws:s3:::{}/*"]
                        }
                    ]
                }""", bucket, bucket);
    }

    @Override
    public String getPathUrl(String path) {
        return minIOProperties.isHttps() ? "https://" : "http://" +
                minIOProperties.getHost() + ":" + minIOProperties.getPort() + "/" + path;
    }

    @Override
    public String getDefaultPhotoPath() {
        return minIOProperties.getBucketName() + "/" + DEFAULT_PHOTO;
    }

    @Override
    public String uploadFile(MultipartFile file, String prefix) {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new ExaminationException(ErrorCode.INVALID_PARAMS);
        }
        filename = prefix + UUID.randomUUID() + filename.lastIndexOf(".");

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minIOProperties.getBucketName()).object(filename)
                    .stream(file.getInputStream(), -1, 5 * 1024 * 1024)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        eventMulticaster.multicastEvent(new FileUploadedEvent(filename));
        return minIOProperties.getBucketName() + "/" + filename;
    }

    @Override
    public String getPhotoPrefix() {
        return PHOTO_PREFIX;
    }

    @Override
    public void deleteFile(String path) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minIOProperties.getBucketName()).object(path)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(List<String> paths) {
        try {
            List<DeleteObject> objects = new ArrayList<>();
            paths.forEach(path -> objects.add(new DeleteObject(path)));
            minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(minIOProperties.getBucketName()).objects(objects)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
