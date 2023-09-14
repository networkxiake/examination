package com.xiaoyao.examination.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.strategy.SaStrategy;
import com.xiaoyao.examination.annotation.CheckLoginAdmin;
import com.xiaoyao.examination.util.AdminStpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
            if (handler instanceof HandlerMethod method) {
                if (SaStrategy.instance.isAnnotationPresent.apply(method.getMethod(), CheckLoginAdmin.class)) {
                    AdminStpUtil.checkLogin();
                }
            }
        })).addPathPatterns("/**");
    }
}
