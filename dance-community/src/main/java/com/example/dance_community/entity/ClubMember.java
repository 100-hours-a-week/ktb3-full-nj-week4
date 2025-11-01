package com.example.dance_community.entity;

import com.example.dance_community.entity.enums.ClubRole;
import com.example.dance_community.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "club_member",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_club",
                        columnNames = {"userId", "clubId"}  // 중복 가입 방지
                )
        }
)
public class ClubMember extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubId", nullable = false)
    private Club club;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClubRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Builder
    private ClubMember(User user, Club club, ClubRole role, UserStatus status) {
        validateClubMember(user, club, role, status);
        this.user = user;
        this.club = club;
        this.role = role;
        this.status = status;
    }

    private void validateClubMember(User user, Club club, ClubRole role, UserStatus status) {
        if (user == null) {
            throw new IllegalArgumentException("클럽 멤버 - 사용자 미입력");
        }
        if (club == null) {
            throw new IllegalArgumentException("클럽 멤버 - 클럽 미입력");
        }
        if (role == null) {
            throw new IllegalArgumentException("클럽 멤버 - 역할 미입력");
        }
        if (status == null) {
            throw new IllegalArgumentException("클럽 멤버 - 상태 미입력");
        }
    }

    public void changeRole(ClubRole newRole) {
        if (newRole == null) throw new IllegalArgumentException("클럽 멤버 - 역할 미입력");
        this.role = newRole;
    }

    public void changeStatus(UserStatus newStatus) {
        if (newStatus == null) throw new IllegalArgumentException("클럽 멤버 - 상태 미입력");
        this.status = newStatus;
    }

    public boolean hasManagementPermission() {
        return this.role == ClubRole.LEADER || this.role == ClubRole.MANAGER;
    }
}