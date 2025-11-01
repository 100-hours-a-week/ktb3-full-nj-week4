package com.example.dance_community.dto.user;

import com.example.dance_community.entity.User;

import java.time.LocalDateTime;

public record UserResponse(
        String email,
        String username,
        String profileImage,
        LocalDateTime createdAt,
        Boolean isDeleted
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getEmail(),
                user.getUsername(),
                user.getProfileImage(),
                user.getCreatedAt(),
                user.getIsDeleted()
        );
    }
}