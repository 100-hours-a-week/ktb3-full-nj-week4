package com.example.dance_community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 100, updatable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String username;

    private String profileImage;

    @Builder
    public User(String email, String password, String username) {
        checkNullOrBlank(email, "이메일");
        checkNullOrBlank(password, "비밀번호");
        checkNullOrBlank(username, "사용자 이름");

        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User updateUser(String password, String username, String profileImage) {
        checkNullOrBlank(password, "비밀번호");
        checkNullOrBlank(username, "사용자 이름");
        if(profileImage == null) {throw new IllegalArgumentException("프로필 사진 미입력");}

        this.username = username;
        this.password = password;
        this.profileImage = profileImage;

        return this;
    }

    private void checkNullOrBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName+" 미입력");
        }
    }
}