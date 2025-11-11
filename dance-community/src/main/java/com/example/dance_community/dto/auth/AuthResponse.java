package com.example.dance_community.dto.auth;

import com.example.dance_community.dto.user.UserResponse;

public record AuthResponse(
        UserResponse userResponse,
        String accessToken,
        String refreshToken
) {
    public static AuthResponse from(UserResponse userResponse, String accessToken, String refreshToken) {
        return new AuthResponse(userResponse, accessToken, refreshToken);
    }
}