package com.example.dance_community.dto.user;

public record UserUpdateRequest(
        String password,
        String username,
        String profileImage) {
}