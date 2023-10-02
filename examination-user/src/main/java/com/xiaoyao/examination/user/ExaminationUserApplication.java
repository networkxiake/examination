package com.xiaoyao.examination.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ConfigurationPropertiesScan
@EnableDubbo
@SpringBootApplication
public class ExaminationUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExaminationUserApplication.class, args);
    }
}
