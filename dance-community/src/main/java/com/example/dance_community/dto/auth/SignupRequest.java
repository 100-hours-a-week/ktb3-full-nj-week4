package com.example.dance_community.dto.auth;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String email;
    private String password;
    private String username;
}
