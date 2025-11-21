package com.example.dance_community.entity;

import com.example.dance_community.enums.ClubJoinStatus;
import com.example.dance_community.enums.ClubRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "clubs")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE clubs SET is_deleted = true WHERE club_id = ?")
public class Club extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @Column(nullable = false, unique = true, length = 100)
    private String clubName;

    @Column(length = 255)
    private String intro;

    @Column(length = 1000)
    private String description;

    @Column(length = 255, columnDefinition = "TEXT")
    private String clubImage;

    private String locationName;

    @ElementCollection
    @CollectionTable(
            name = "club_tags",
            joinColumns = @JoinColumn(name = "clubId")
    )
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubJoin> members = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    // CREATE
    @Builder
    public Club(String clubName, String intro, String description, String clubImage, String locationName, List<String> tags) {
        checkNullOrBlank(clubName, "클럽 이름");
        checkNullOrBlank(intro, "클럽 한 줄 소개");
        checkNullOrBlank(locationName, "클럽 위치");
        checkNullOrBlank(description, "클럽 설명");

        this.clubName = clubName;
        this.intro = intro;
        this.clubImage = clubImage;
        this.locationName = locationName;
        this.description = description;
        this.tags = tags;
    }

    // READ
    public int getMemberCount() {
        return members.size();
    }

    // UPDATE
    public Club updateClub(String clubName, String intro, String description, String clubImage, String locationName,  List<String> tags) {
        checkNullOrBlank(clubName, "클럽 이름");
        checkNullOrBlank(intro, "클럽 한 줄 소개");
        checkNullOrBlank(locationName, "클럽 위치");
        checkNullOrBlank(description, "클럽 설명");

        this.clubName = clubName;
        this.intro = intro;
        this.clubImage = clubImage;
        this.locationName = locationName;
        this.description = description;
        this.tags = tags;

        return this;
    }

    // Convenience Methods for ClubJoin
    public void addMember(User user, ClubRole role, ClubJoinStatus status) {
        ClubJoin newClubJoin = ClubJoin.builder()
                .user(user)
                .club(this)
                .role(role)
                .status(status)
                .build();
        this.members.add(newClubJoin);
        user.getClubs().add(newClubJoin);
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