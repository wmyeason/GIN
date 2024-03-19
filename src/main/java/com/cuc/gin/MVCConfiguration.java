package com.cuc.gin;

import com.cuc.gin.controller.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : Wang SM.
 * @since : 2024/3/2,  
 **/
@Configuration
public class MVCConfiguration implements WebMvcConfigurer {
    @Bean
    public AuthInterceptor userInterceptor() {
        return new AuthInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor());
    }
}
