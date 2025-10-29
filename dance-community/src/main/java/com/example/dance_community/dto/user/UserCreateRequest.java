package com.example.dance_community.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
        @NotBlank(message = "이메일 미입력")
        String email,
        @NotBlank(message = "비밀번호 미입력")
        String password,
        @NotBlank(message = "사용자 이름 미입력")
        String username) {
}