package com.example.dance_community.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String email;
    private String password;
    private String username;
    private String profileImage;
    private LocalDateTime createdAt;
    private Boolean isDeleted;

    public void hashedPassword() {
        this.password = BCrypt.hashpw(this.password, BCrypt.gensalt());
    }

    public User updateDetails(String password, String username, String profileImage) {
        return this.toBuilder()
                .password(password)
                .username(username)
                .profileImage(profileImage)
                .build();
    }
}