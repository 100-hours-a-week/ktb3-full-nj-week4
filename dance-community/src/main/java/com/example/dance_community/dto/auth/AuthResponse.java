package com.example.dance_community.dto.auth;

public class AuthResponse {
    private String message;
    private AuthDto data;

    public AuthResponse() {
    }
    public AuthResponse(String message, AuthDto data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public AuthDto getAuthDto() {
        return data;
    }
    public void setAuthDto(AuthDto data) {
        this.data = data;
    }
}
