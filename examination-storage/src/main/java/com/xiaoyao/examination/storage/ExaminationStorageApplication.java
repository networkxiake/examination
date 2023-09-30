package com.xiaoyao.examination.storage;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConfigurationPropertiesScan
@EnableScheduling
@EnableDubbo
@SpringBootApplication
public class ExaminationStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExaminationStorageApplication.class, args);
    }
}
