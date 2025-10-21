package com.example.dance_community.dto.user;

import lombok.Getter;

@Getter
public class UserRequest {
    private String password;
    private String username;
    private Long clubId;
    private String profileImage;
}
