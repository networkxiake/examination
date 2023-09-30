package com.xiaoyao.examination.api.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.strategy.SaStrategy;
import com.xiaoyao.examination.api.annotation.CheckLoginAdmin;
import com.xiaoyao.examination.api.annotation.CheckLoginInitAdmin;
import com.xiaoyao.examination.api.annotation.CheckLoginUser;
import com.xiaoyao.examination.api.util.AdminStpUtil;
import com.xiaoyao.examination.api.util.UserStpUtil;
import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.common.interfaces.user.AdminService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @DubboReference
    private AdminService adminDomainService;

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
