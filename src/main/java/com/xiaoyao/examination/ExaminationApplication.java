package com.xiaoyao.examination;

import cn.felord.payment.autoconfigure.EnableMobilePay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableMobilePay
@EnableScheduling
@EnableAsync
@ConfigurationPropertiesScan
@SpringBootApplication
public class ExaminationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExaminationApplication.class, args);
    }
}
