package com.example.dance_community.entity;

import com.example.dance_community.entity.enums.ClubRole;
import com.example.dance_community.entity.enums.ClubJoinStatus;
import com.example.dance_community.entity.enums.EventJoinStatus;
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
    private List<ClubJoin> clubs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventJoin> eventJoins = new ArrayList<>();

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

    // Convenience Methods for ClubJoin
    public void addClubJoin(Club club, ClubRole role, ClubJoinStatus status) {
        ClubJoin newClubJoin = ClubJoin.builder()
                .user(this)
                .club(club)
                .role(role)
                .status(status)
                .build();
        this.clubs.add(newClubJoin);
        club.getMembers().add(newClubJoin);
    }
    public void removeClubJoin(Club club) {
        clubs.removeIf(m -> m.getClub().equals(club));
        club.getMembers().removeIf(m -> m.getUser().equals(this));
    }

    // Convenience Methods for Post
    public void addPost(Post post) {
        this.posts.add(post);
        post.setAuthor(this);
    }
    public void removePost(Post post) {
        this.posts.remove(post);
        post.setAuthor(null);
    }

    // Convenience Methods for Event
    public void addEvent(Event event) {
        this.events.add(event);
        event.setHost(this);
    }
    public void removeEvent(Event event) {
        this.events.remove(event);
        event.setHost(null);
    }

    // Convenience Methods for EventJoin
    public void addEventJoin(Event event, EventJoinStatus status) {
        EventJoin newEventJoin = EventJoin.builder()
                .participant(this)
                .event(event)
                .status(status)
                .build();
        this.eventJoins.add(newEventJoin);
        event.getParticipants().add(newEventJoin);
    }
    public void removeEventJoin(Event event) {
        eventJoins.removeIf(ej -> ej.getEvent().equals(event));
        event.getParticipants().removeIf(ej -> ej.getParticipant().equals(this));
    }

    // Check Methods
    private void checkNullOrBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName+" 미입력");
        }
    }
}