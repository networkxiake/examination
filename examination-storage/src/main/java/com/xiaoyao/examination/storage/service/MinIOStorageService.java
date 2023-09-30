package com.xiaoyao.examination.storage.service;

import cn.hutool.core.util.StrUtil;
import com.xiaoyao.examination.storage.properties.MinIOProperties;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@DubboService
@ConditionalOnProperty(prefix = "storage", name = "type", havingValue = "minio")
public class MinIOStorageService extends BaseStorageService {
    private final MinioClient client;
    private final MinIOProperties properties;

    public MinIOStorageService(MinioClient client, MinIOProperties properties,
                               RedissonClient redissonClient, StringRedisTemplate redisTemplate) {
        super(redisTemplate, redissonClient);
        this.client = client;
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() {
        super.initBucket();
    }

    @Override
    public String getExcelDownloadingUrl(String filename) {
        return getPathDownloadingUrl(GOODS_EXCEL_PREFIX + filename);
    }

    @Override
    public String getPathDownloadingUrl(String path) {
        return properties.isHttps() ? "https://" : "http://" + properties.getHost() + ":" + properties.getPort() + "/" +
                properties.getBucketName() + "/" + path;
    }

    @Override
    protected boolean isNotBucketExist() {
        try {
            return !client.bucketExists(BucketExistsArgs.builder()
                    .bucket(properties.getBucketName())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void createPublicBucket() {
        try {
            String config = """
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
                    }""";
            client.makeBucket(MakeBucketArgs.builder()
                    .bucket(properties.getBucketName())
                    .build());
            client.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(properties.getBucketName())
                    .config(StrUtil.format(config, properties.getBucketName(), properties.getBucketName()))
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String getPreSignatureUrl(String path, int duration, TimeUnit unit) {
        try {
            return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(properties.getBucketName()).object(path)
                    .method(Method.PUT)
                    .expiry(duration, unit)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean hasFileInSpace(String path) {
        Iterable<Result<Item>> results = client.listObjects(ListObjectsArgs.builder()
                .bucket(properties.getBucketName())
                .recursive(true)
                .prefix(path)
                .build());
        return results.iterator().hasNext();
    }

    @Override
    protected void deleteSpace(List<String> paths) {
        try {
            List<DeleteObject> objects = new ArrayList<>();
            paths.forEach(path -> objects.add(new DeleteObject(path)));
            client.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(properties.getBucketName()).objects(objects)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void uploadUserDefaultPhoto(String path, String filePath, String mimeType) {
        try {
            client.putObject(PutObjectArgs.builder()
                    .bucket(properties.getBucketName())
                    .object(path)
                    .stream(getClass().getClassLoader().getResourceAsStream(filePath),
                            -1, 5 * 1024 * 1024)
                    .contentType(mimeType)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0 0 3 * * ?")
    @Override
    public void scheduledTask() {
        super.autoReleaseSpace();
    }
}
