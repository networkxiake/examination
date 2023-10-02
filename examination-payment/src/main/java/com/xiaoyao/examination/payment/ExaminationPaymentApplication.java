package com.xiaoyao.examination.payment;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class ExaminationPaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExaminationPaymentApplication.class, args);
    }
}
