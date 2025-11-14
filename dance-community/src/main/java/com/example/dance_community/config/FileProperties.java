package com.example.dance_community.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileProperties {
    private String baseDir = "uploads";        // 기본 디렉토리
    private String profileDir = "profiles";    // 프로필 이미지 서브 디렉토리
    private String postDir = "posts";          // 게시글 이미지 (나중에)
    private String defaultProfile = "/images/default-profile.png";  // 디폴트 이미지
}