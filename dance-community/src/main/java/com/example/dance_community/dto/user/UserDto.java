/**
 * UserDto
 * *여기서부터 lombok 라이브러리 적용*
 */

package com.example.dance_community.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private String username;
    private String profileImage;

    private Long userId;
    private Long clubId;

    public UserDto() {
    }
}