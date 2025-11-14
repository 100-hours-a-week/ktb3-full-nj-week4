package com.example.dance_community.config;

import com.example.dance_community.auth.GetUserIdResolver;
import com.example.dance_community.jwt.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    public final GetUserIdResolver getUserIdResolver;

    public WebConfig(JwtInterceptor jwtInterceptor, GetUserIdResolver getUserIdResolver) {
        this.jwtInterceptor = jwtInterceptor;
        this.getUserIdResolver = getUserIdResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/auth/refresh", "/users/**", "/clubs/**", "/club-joins/**", "/posts/**", "/events/**", "/event-joins/**")
                .excludePathPatterns("/auth/login", "/auth/signup");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(getUserIdResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
