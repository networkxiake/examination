package com.xiaoyao.examination.config;

import com.ql.util.express.ExpressRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QLExpressConfig {
    @Bean
    public ExpressRunner expressRunner() {
        return new ExpressRunner(true, false);
    }
}
