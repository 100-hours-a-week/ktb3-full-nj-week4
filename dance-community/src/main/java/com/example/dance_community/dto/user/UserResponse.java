package com.example.dance_community.dto.user;

import java.time.LocalDateTime;

public record UserResponse(
        String email,
        String username,
        String profileImage,
        LocalDateTime createdAt,
        Boolean isDeleted) {
    public static UserResponse from(com.example.dance_community.entity.User entity) {
        return new UserResponse(
                entity.getEmail(),
                entity.getUsername(),
                entity.getProfileImage(),
                entity.getCreatedAt(),
                entity.getIsDeleted()
        );
}}