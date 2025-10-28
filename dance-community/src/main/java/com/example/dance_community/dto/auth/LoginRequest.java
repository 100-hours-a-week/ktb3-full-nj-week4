package com.example.dance_community.dto.auth;

import jakarta.validation.constraints.*;

public record LoginRequest(
        @Email(message = "이메일 형식 오류")
        @NotBlank(message = "이메일 미입력")
        String email,

        @NotBlank(message = "비밀번호 미입력")
        String password
) {
}