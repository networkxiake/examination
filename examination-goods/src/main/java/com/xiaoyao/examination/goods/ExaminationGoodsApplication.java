package com.xiaoyao.examination.goods;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class ExaminationGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExaminationGoodsApplication.class, args);
    }
}
