package com.example.dance_community.entity;

import jakarta.persistence.*;
import lombok.*;
import org.mindrot.jbcrypt.BCrypt;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
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

    /// 유효성 검사 어디서 할지? 서비스? 레포?
    public User(String email, String password, String username) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("이메일 미입력");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("비밀번호 미입력");
        if (username == null || username.isBlank()) throw new IllegalArgumentException("사용자 이름 미입력");
        this.email = email;
        this.password = hashedPassword(password);
        this.username = username;
    }

    /// TODO : 유효성 검사 서비스 계층으로 옮기기?
    public User updateUser(String password, String username, String profileImage) {
        if (username != null) this.username = username;
        if (profileImage != null) this.profileImage = profileImage;

        return this;
    }

    /// TODO : 서비스 계층으로 옮기기? 또는 유틸성 클래스 생성 또는 패스워드 암호화 전담 클래스 생성 또는 AOP 적용 또는 필터 적용 또는 인터셉터 적용
    private String hashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /// 5. 생성일/삭제여부 초기화 누락
    ///    @PrePersist로 자동화
    ///
    /// 6. BaseEntity 추상화 부재
    ///    createdAt, isDeleted 공통화
    /// 소프트 딜리트 추가?
}