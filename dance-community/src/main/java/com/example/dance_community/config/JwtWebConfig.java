package com.example.dance_community.config;

import com.example.dance_community.jwt.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtWebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    public JwtWebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/auth/refresh", "/users/**", "/posts/**", "/events/**")
                .excludePathPatterns("/auth/login", "/auth/signup");
    }
}
