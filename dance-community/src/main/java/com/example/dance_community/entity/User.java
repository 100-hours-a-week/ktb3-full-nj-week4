package com.example.dance_community.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(length = 255)
    private String profileImage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean isDeleted;

    public User(String email, String password, String username) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("이메일 미입력");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("비밀번호 미입력");
        if (username == null || username.isBlank()) throw new IllegalArgumentException("사용자 이름 미입력");
        this.email = email;
        this.password = password;
        this.username = username;
        this.createdAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    /// TODO : 유효성 검사 서비스 계층으로 옮기기?
    public User updateUser(String password, String username, String profileImage) {
        if (username != null) this.username = username;
        if (profileImage != null) this.profileImage = profileImage;

        return this;
    }

    /// 5. 생성일/삭제여부 초기화 누락
    ///    @PrePersist로 자동화
    ///
    /// 6. BaseEntity 추상화 부재
    ///    createdAt, isDeleted 공통화
    /// 소프트 딜리트 추가?
}