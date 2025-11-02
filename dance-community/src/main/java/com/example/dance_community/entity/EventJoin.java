package com.example.dance_community.entity;

import com.example.dance_community.entity.enums.ClubRole;
import com.example.dance_community.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "registration",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_event",
                        columnNames = {"userId", "eventId"}  // Unique 설정 -> 중복 신청 방지
                )
        }
)
public class EventJoin extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId", nullable = false)
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClubRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    // CREATE
    @Builder
    private ClubMember(User user, Club club, ClubRole role, UserStatus status) {
        validateClubMember(user, club, role, status);
        this.user = user;
        this.club = club;
        this.role = role;
        this.status = status;
    }

    // READ
    public boolean hasManagementPermission() {
        return this.role == ClubRole.LEADER || this.role == ClubRole.MANAGER;
    }

    // UPDATE
    public void changeRole(ClubRole newRole) {
        if (newRole == null) throw new IllegalArgumentException("클럽 멤버 - 역할 미입력");
        this.role = newRole;
    }
    public void changeStatus(UserStatus newStatus) {
        if (newStatus == null) throw new IllegalArgumentException("클럽 멤버 - 상태 미입력");
        this.status = newStatus;
    }

    // Check Methods
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
}