package com.example.dance_community.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequest{
    String password;
    String nickname;
    String profileImage;
}