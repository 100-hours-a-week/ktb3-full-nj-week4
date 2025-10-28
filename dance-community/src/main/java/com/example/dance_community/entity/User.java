package com.example.dance_community.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 100, updatable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String username;

    private String profileImage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean isDeleted;

    public User(String email, String password, String username) {
        checkNullOrBlank(email, "이메일");
        checkNullOrBlank(password, "비밀번호");
        checkNullOrBlank(username, "사용자 이름");

        this.email = email;
        this.password = password;
        this.username = username;
        this.createdAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    public User updateUser(String password, String username, String profileImage) {
        checkNullOrBlank(password, "비밀번호");
        checkNullOrBlank(username, "사용자 이름");
        if(profileImage == null) {
            profileImage = "";
        }

        this.username = username;
        this.password = password;
        this.profileImage = profileImage;

        return this;
    }

    public void deleteUser() {
        this.isDeleted = true;
    }

    private void checkNullOrBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName+" 미입력");
        }
    }
}