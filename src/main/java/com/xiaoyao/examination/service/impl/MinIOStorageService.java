package com.xiaoyao.examination.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.properties.MinIOProperties;
import com.xiaoyao.examination.service.StorageService;
import io.minio.*;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinIOStorageService implements StorageService {
    private final String DEFAULT_PHOTO = "default-photo.jpg";
    private final String EXCEL_PREFIX = "excel/";
    private final String TEMP_FILE_KEY = "temp-file";

    private final MinIOProperties minIOProperties;
    private final MinioClient minioClient;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate redisTemplate;

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
    public String getPathDownloadingUrl(String path) {
        return minIOProperties.isHttps() ? "https://" : "http://" +
                minIOProperties.getHost() + ":" + minIOProperties.getPort() + "/" +
                minIOProperties.getBucketName() + "/" + path;
    }

    @Override
    public void confirmTempFile(String path) {
        redisTemplate.opsForZSet().remove(TEMP_FILE_KEY, path.substring(minIOProperties.getBucketName().length() + 1));
    }

    @Override
    public String getDefaultPhotoPath() {
        return minIOProperties.getBucketName() + "/" + DEFAULT_PHOTO;
    }

    @Override
    public String uploadTempUserPhoto(long userId, MultipartFile file) {
        String filename = file.getOriginalFilename();
        checkPhotoType(filename);

        String path = "photo/user/" + userId + filename.substring(filename.lastIndexOf("."));
        uploadTempFile(path, file);
        return path;
    }

    @Override
    public void deleteUserPhoto(List<String> paths) {
        // 不能删除默认头像
        paths = new ArrayList<>(paths);    // 将参数转换为可变的
        String defaultPhotoPath = getDefaultPhotoPath();
        paths.removeIf(path -> path.equals(defaultPhotoPath));
        if (!paths.isEmpty()) {
            deleteFile(paths);
        }
    }

    @Override
    public String uploadTempGoodsPhoto(long goodsId, MultipartFile file) {
        String filename = file.getOriginalFilename();
        checkPhotoType(filename);

        String path = "photo/goods/" + goodsId + filename.substring(filename.lastIndexOf("."));
        uploadTempFile(path, file);
        return path;
    }

    private void checkPhotoType(String filename) {
        if (filename == null) {
            throw new ExaminationException(ErrorCode.INVALID_PARAMS);
        } else if (!(filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg"))) {
            throw new ExaminationException(ErrorCode.PHOTO_TYPE_ERROR);
        }
    }

    @Override
    public void uploadExcel(long goodsId, MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.endsWith(".xlsx")) {
            throw new ExaminationException(ErrorCode.INVALID_PARAMS);
        }
        uploadFile(EXCEL_PREFIX + goodsId + ".xlsx", file);
    }

    @Override
    public String getExcelDownloadingUrl(long goodsId) {
        return getPathDownloadingUrl(minIOProperties.getBucketName() + "/" + EXCEL_PREFIX + goodsId + ".xlsx");
    }

    @Override
    public void deleteExcel(List<Long> goodsIds) {
        List<String> path = new ArrayList<>();
        goodsIds.forEach(id -> path.add(EXCEL_PREFIX + id + ".xlsx"));
        deleteFile(path);
    }

    private void uploadFile(String shortPath, MultipartFile file) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minIOProperties.getBucketName()).object(shortPath)
                    .stream(file.getInputStream(), -1, 5 * 1024 * 1024)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void uploadTempFile(String shortPath, MultipartFile file) {
        redisTemplate.opsForZSet().add(TEMP_FILE_KEY, shortPath, System.currentTimeMillis());
        uploadFile(shortPath, file);
    }

    private void deleteFile(List<String> shortPaths) {
        try {
            List<DeleteObject> objects = new ArrayList<>();
            shortPaths.forEach(path -> objects.add(new DeleteObject(path)));
            minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(minIOProperties.getBucketName()).objects(objects)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 每天凌晨3点自动清除一天前的临时文件。
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void clearTempFile() {
        String LAST_REST_DATE_KEY = "last-reset-date";

        String lastRestDate = redisTemplate.opsForValue().get(LAST_REST_DATE_KEY);
        if (!LocalDate.now().toString().equals(lastRestDate)) {
            RLock lock = redissonClient.getLock("clearTempFile");
            if (lock.tryLock()) {
                try {
                    lastRestDate = redisTemplate.opsForValue().get(LAST_REST_DATE_KEY);
                    if (!LocalDate.now().toString().equals(lastRestDate)) {
                        Set<String> members = redisTemplate.opsForZSet().rangeByScore(TEMP_FILE_KEY,
                                0, System.currentTimeMillis() - 24 * 60 * 60 * 1000);
                        if (members != null && !members.isEmpty()) {
                            redisTemplate.opsForZSet().remove(TEMP_FILE_KEY, members);
                            deleteFile(new ArrayList<>(members));
                        }
                        redisTemplate.opsForValue().set(LAST_REST_DATE_KEY, LocalDate.now().toString());
                        if (log.isInfoEnabled()) {
                            log.info("清除一天前的临时文件");
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
