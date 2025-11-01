package com.example.dance_community.entity;

import com.example.dance_community.entity.enums.EventType;
import com.example.dance_community.entity.enums.Scope;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    // Identifiers(수정 불가)
    private Long eventId;
    private Long userId;

    // Classification(수정 불가)
    private Scope scope;
    private Long clubId;
    private EventType type;

    // Details
    private String title;
    private String content;
    private List<String> tags;
    private List<String> images;

    // Location
    private String locationName;
    private String locationAddress;
    private String locationLink;

    // Capacity
    private Long capacity;
    private Long currentParticipants;

    // Schedule
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Event updateDetails(String title, String content, List<String> tags, List<String> images) {
        return this.toBuilder()
                .title(title)
                .content(content)
                .tags(tags)
                .images(images)
                .build();
    }

    public Event updateLocation(String locationName, String locationAddress, String locationLink) {
        return this.toBuilder()
                .locationName(locationName)
                .locationAddress(locationAddress)
                .locationLink(locationLink)
                .build();
    }

    public Event updateSchedule(LocalDateTime startsAt, LocalDateTime endsAt) {
        if (endsAt.isBefore(startsAt)) {
            throw new IllegalArgumentException("시간 설정 오류");
        }
        return this.toBuilder()
                .startsAt(startsAt)
                .endsAt(endsAt)
                .build();
    }

    public Event updateCapacity(Long capacity) {
        return this.toBuilder()
                .capacity(capacity)
                .build();
    }

    public Event updateTime() {
        return this.toBuilder()
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void incrementParticipants() {
        if (this.currentParticipants == null) {
            this.currentParticipants = 0L;
        }
        if (this.capacity != null && this.currentParticipants >= this.capacity) {
            throw new IllegalStateException("행사 신청 실패(신청 마감)");
        }
        this.currentParticipants = this.currentParticipants + 1;
    }
    public void decrementParticipants() {
        if (this.currentParticipants == null || this.currentParticipants <= 0) {
            this.currentParticipants = 0L;
            return;
        }
        this.currentParticipants = this.currentParticipants - 1;
    }
}