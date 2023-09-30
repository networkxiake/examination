package com.xiaoyao.examination.storage.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage.minio")
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

    public boolean isHttps() {
        return isHttps;
    }

    public void setHttps(boolean https) {
        isHttps = https;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
