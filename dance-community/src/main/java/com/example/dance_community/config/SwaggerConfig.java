package com.example.dance_community.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;                     // <- 올바른 임포트
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "DANCE COMMUNITY API", version = "v1"),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth", // 보안 스킴의 이름
        type = SecuritySchemeType.HTTP, // 인증 타입
        scheme = "bearer", // 스킴 유형
        bearerFormat = "JWT" // 토큰 형식
)
@Configuration
public class SwaggerConfig {
}