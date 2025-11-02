package com.example.dance_community.entity;

import com.example.dance_community.entity.enums.ClubRole;
import com.example.dance_community.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(length = 255)
    private String profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMember> clubs = new ArrayList<>();

    // CREATE
    @Builder
    public User(String email, String password, String username, String profileImage) {
        checkNullOrBlank(email, "이메일");
        checkNullOrBlank(password, "비밀번호");
        checkNullOrBlank(username, "사용자 이름");

        this.email = email;
        this.password = password;
        this.username = username;
        this.profileImage = profileImage;
    }

    // UPDATE
    public User updateUser(String password, String username, String profileImage) {
        checkNullOrBlank(password, "비밀번호");
        checkNullOrBlank(username, "사용자 이름");

        this.username = username;
        this.password = password;
        this.profileImage = profileImage;

        return this;
    }

    // Convenience Methods for ClubMember->Club
    public void AddClub(Club club, ClubRole role, UserStatus status) {
        ClubMember newClubMember = ClubMember.builder()
                .user(this)
                .club(club)
                .role(role)
                .status(status)
                .build();
        this.clubs.add(newClubMember);
        club.getMembers().add(newClubMember);
    }
    public void removeClub(Club club) {
        clubs.removeIf(m -> m.getClub().equals(club));
        club.getMembers().removeIf(m -> m.getUser().equals(this));
    }

    // Check Methods
    private void checkNullOrBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName+" 미입력");
        }
    }
}