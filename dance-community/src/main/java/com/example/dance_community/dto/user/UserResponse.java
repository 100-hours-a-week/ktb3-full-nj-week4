package com.example.dance_community.dto.user;

import com.example.dance_community.entity.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long userId,
        String email,
        String username,
        String profileImage,
        LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getEmail(),
                user.getUsername(),
                user.getProfileImage(),
                user.getCreatedAt()
        );
    }
}