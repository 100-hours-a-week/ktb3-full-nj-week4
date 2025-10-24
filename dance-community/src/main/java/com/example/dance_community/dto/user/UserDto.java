package com.example.dance_community.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private String username;
    private String profileImage;

    private Long userId;
    private Long clubId;

    public static UserDto changePassword(Optional<UserDto> user) {
        return user.toBuilder()
                .password(null)
                .build();
    }
}