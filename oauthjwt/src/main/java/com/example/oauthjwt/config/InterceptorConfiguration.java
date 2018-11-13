package com.example.oauthjwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class InterceptorConfiguration extends WebMvcSecurityConfiguration {
  @Autowired
  MyInterceptor myInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(myInterceptor);
        // 配置拦截的路径
        ir.addPathPatterns("/oauth/token");
        // 配置不拦截的路径
        ir.excludePathPatterns("/**.html");

    }
}