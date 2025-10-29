package com.example.dance_community.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthDto {
    private final Long userId;
    private String accessToken;
    private String refreshToken;

    public AuthDto(Long userId) {
        this.userId = userId;
    }
}
