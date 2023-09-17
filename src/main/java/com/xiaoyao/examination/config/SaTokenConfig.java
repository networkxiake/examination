package com.xiaoyao.examination.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.strategy.SaStrategy;
import com.xiaoyao.examination.annotation.CheckLoginAdmin;
import com.xiaoyao.examination.annotation.CheckLoginInitAdmin;
import com.xiaoyao.examination.annotation.CheckLoginUser;
import com.xiaoyao.examination.domain.service.AdminDomainService;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.util.AdminStpUtil;
import com.xiaoyao.examination.util.UserStpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class SaTokenConfig implements WebMvcConfigurer {
    private final AdminDomainService adminDomainService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
            if (handler instanceof HandlerMethod method) {
                if (SaStrategy.instance.isAnnotationPresent.apply(method.getMethod(), CheckLoginAdmin.class)) {
                    AdminStpUtil.checkLogin();
                }
                if (SaStrategy.instance.isAnnotationPresent.apply(method.getMethod(), CheckLoginInitAdmin.class)) {
                    if (!adminDomainService.isInitAdmin(AdminStpUtil.getLoginId())) {
                        throw new ExaminationException(ErrorCode.NEED_INIT_ADMIN);
                    }
                }
                if (SaStrategy.instance.isAnnotationPresent.apply(method.getMethod(), CheckLoginUser.class)) {
                    UserStpUtil.checkLogin();
                }
            }
        })).addPathPatterns("/**");
    }
}
