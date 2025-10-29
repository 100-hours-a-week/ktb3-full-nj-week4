package com.example.dance_community.config;

import com.example.dance_community.auth.GetUserIdResolver;
import com.example.dance_community.jwt.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class JwtWebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    public final GetUserIdResolver getUserIdResolver;

    public JwtWebConfig(JwtInterceptor jwtInterceptor, GetUserIdResolver getUserIdResolver) {
        this.jwtInterceptor = jwtInterceptor;
        this.getUserIdResolver = getUserIdResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/auth/refresh", "/users/**", "/posts/**", "/events/**", "/registration/**")
                .excludePathPatterns("/auth/login", "/auth/signup");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(getUserIdResolver);
    }
}
