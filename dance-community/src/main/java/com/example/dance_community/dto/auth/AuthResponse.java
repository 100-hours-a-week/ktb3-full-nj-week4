package com.example.dance_community.dto.auth;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse {
    private final String message;
    private final AuthDto data;
}
