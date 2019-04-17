package com.deshmukhamit.springbootmysql.controller;

import com.deshmukhamit.springbootmysql.interceptor.AuthHeaderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class BaseAuthController implements WebMvcConfigurer {
    @Autowired
    private AuthHeaderInterceptor authHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHeaderInterceptor);
    }
}
