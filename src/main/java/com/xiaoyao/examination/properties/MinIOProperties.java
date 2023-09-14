package com.xiaoyao.examination.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "minio")
public class MinIOProperties {
    /**
     * 是否使用https。
     */
    private boolean isHttps = false;

    /**
     * 服务器地址。
     */
    private String host;

    /**
     * 服务器端口。
     */
    private int port = 9000;

    /**
     * 访问key。
     */
    private String accessKey;

    /**
     * 访问密钥。
     */
    private String secretKey;

    /**
     * 存储桶名称。
     */
    private String bucketName;
}
