package com.dh.configure;

import com.dh.interceptor.CheckUserInterceptor;
import com.dh.interceptor.RequestInterceptor;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class CustomWebMvcConfigurerAdapter extends WebMvcAutoConfigurationAdapter {

    @Bean
    public CheckUserInterceptor checkUserInterceptor() {
        return new CheckUserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 对来自/api/** 这个链接来的请求进行拦截
        registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/api/**");
        // 对来自/api/** 这个链接来的请求进行拦截 排除/api/user/的链接
        registry.addInterceptor(checkUserInterceptor()).addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/**");

    }
}
