package com.xiaoyao.examination.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class ExaminationOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExaminationOrderApplication.class, args);
    }
}
