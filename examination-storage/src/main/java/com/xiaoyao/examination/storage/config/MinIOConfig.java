package com.xiaoyao.examination.storage.config;

import com.xiaoyao.examination.storage.properties.MinIOProperties;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOConfig {
    private final MinIOProperties properties;

    public MinIOConfig(MinIOProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(properties.getHost(), properties.getPort(), properties.isHttps())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }
}
