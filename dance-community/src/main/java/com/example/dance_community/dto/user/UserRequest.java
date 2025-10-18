package com.example.dance_community.dto.user;

import lombok.Data;

@Data
public class UserRequest {
    private String password;
    private String username;
    private Long clubId;
    private String profileImage;
}
