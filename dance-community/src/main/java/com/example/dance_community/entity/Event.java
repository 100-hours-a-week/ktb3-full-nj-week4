package com.example.dance_community.entity;

import com.example.dance_community.entity.enums.EventType;
import com.example.dance_community.entity.enums.Scope;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "events")
public class Event extends BaseEntity{

    // 행사 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false ,updatable = false)
    private User author;

    // 공개 범위
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private Scope scope;

    // 클럽 ID (Scope.CLUB일 때 대상 클럽)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubId", updatable = false)
    private Club club;

    // 행사 유형
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private EventType type;

    // 행사 관련 내용 (제목, 내용, 태그, 이미지)
    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @ElementCollection
    @CollectionTable(
            name = "event_tags",
            joinColumns = @JoinColumn(name = "eventId")
    )
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "event_images",
            joinColumns = @JoinColumn(name = "eventId")
    )
    @Column(name = "image")
    private List<String> images = new ArrayList<>();

    // 행사 장소 정보 (이름, 주소, 링크)
    private String locationName;
    private String locationAddress;
    private String locationLink;

    // 행사 총 수용 인원
    @Column(nullable = false)
    private Long capacity;

    // 행사 참가자 목록
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventJoin> eventJoins = new ArrayList<>();

    // 행사 일시 (시작, 종료 시간)
    @Column(nullable = false)
    private LocalDateTime startsAt;
    @Column(nullable = false)
    private LocalDateTime endsAt;

    // 수정 일시
    private LocalDateTime updatedAt;

    // CREATE
    @Builder
    private Event(User author, Scope scope, Club club, EventType type,
                  String title, String content, List<String> tags, List<String> images,
                  String locationName, String locationAddress, String locationLink, Long capacity,
                  LocalDateTime startsAt, LocalDateTime endsAt) {
        validateEvent(author, scope, club, type, title, content, capacity, startsAt, endsAt);

        this.author = author;
        this.scope = scope;
        this.club = club;
        this.type = type;
        this.title = title;
        this.content = content;
        this.tags = tags == null ? new ArrayList<>() : new ArrayList<>(tags);
        this.images = images == null ? new ArrayList<>() : new ArrayList<>(images);
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.locationLink = locationLink;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    // UPDATE
    public Event updatePost(String title, String content, List<String> tags, List<String> images,
                            String locationName, String locationAddress, String locationLink, Long capacity,
                            LocalDateTime startsAt, LocalDateTime endsAt) {
        checkNullOrBlank(title, "제목");
        checkNullOrBlank(content, "내용");


        this.title = title;
        this.content = content;
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        this.images = images != null ? new ArrayList<>(images) : new ArrayList<>();
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.locationLink = locationLink;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.updatedAt = LocalDateTime.now();

        return this;
    }

    // Check Methods
    private void checkNullOrBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName+" 미입력");
        }
    }
    private void validateEvent(User author, Scope scope, Club club, EventType type,
                               String title, String content, Long capacity,
                               LocalDateTime startsAt, LocalDateTime endsAt) {
        if (author == null) {
            throw new IllegalArgumentException("작성자 미입력");
        }
        if (scope == null) {
            throw new IllegalArgumentException("공개 범위 미입력");
        }
        if (scope == Scope.CLUB && club == null) {
            throw new IllegalArgumentException("공개 범위 : 클럽 -> 클럽 값 필요");
        }
        if (scope == Scope.GLOBAL && club != null) {
            throw new IllegalArgumentException("공개 범위 : 전체 -> 클럽 값 null 이어야 함");
        }
        if (type == null) {
            throw new IllegalArgumentException("행사 유형 미입력");
        }
        checkNullOrBlank(title, "제목");
        checkNullOrBlank(content, "내용");
        if (capacity == null || capacity <= 0) {
            throw new IllegalArgumentException("행사 총 수용 인원 미입력 또는 0 이하");
        }
        if (startsAt == null) {
            throw new IllegalArgumentException("행사 시작 일시 미입력");
        }
        if (endsAt == null) {
            throw new IllegalArgumentException("행사 종료 일시 미입력");
        }
    }
}