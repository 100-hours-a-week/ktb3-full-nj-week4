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
@Table(name = "clubs")
public class Club extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @Column(nullable = false, unique = true, length = 100)
    private String clubName;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMember> members = new ArrayList<>();

    // CREATE
    @Builder
    public Club(String clubName, String description) {
        if (clubName == null || clubName.isBlank()) {
            throw new IllegalArgumentException("클럽 이름 미입력");
        }

        this.clubName = clubName;
        this.description = description;
    }

    // READ
    public int getMemberCount() {
        return members.size();
    }

    // UPDATE
    public Club updateClub(String description) {
        this.description = description;
        return this;
    }

    // Convenience Methods for ClubMember->User
    public void addMember(User user, ClubRole role, UserStatus status) {
        ClubMember newClubMember = ClubMember.builder()
                .user(user)
                .club(this)
                .role(role)
                .status(status)
                .build();
        this.members.add(newClubMember);
        user.getClubs().add(newClubMember);
    }
    public void removeMember(User user) {
        members.removeIf(m -> m.getUser().equals(user));
        user.getClubs().removeIf(m -> m.getClub().equals(this));
    }

    // Check Methods
    private void checkNullOrBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName+" 미입력");
        }
    }
}