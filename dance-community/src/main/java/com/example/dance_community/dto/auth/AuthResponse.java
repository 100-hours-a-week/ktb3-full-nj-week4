package com.example.dance_community.dto.auth;

import com.example.dance_community.dto.user.UserResponse;

public record AuthResponse(
        UserResponse userResponse,
        String accessToken
) {
    public static AuthResponse from(UserResponse userResponse, String accessToken) {
        return new AuthResponse(userResponse, accessToken);
    }
}