package com.example.dance_community.entity;

import com.example.dance_community.dto.event.EventDto;
import com.example.dance_community.dto.event.Location;
import com.example.dance_community.enums.EventType;
import com.example.dance_community.enums.Scope;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Long eventId;
    private Long userId;
    private Long clubId;

    private Scope scope;
    private EventType type;
    private String title;
    private String content;
    private List<String> tags;
    private List<String> images;
    private Location location;
    private Long capacity;
    private Long currentParticipants;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public EventDto toDto() {
        return EventDto.builder()
                .eventId(this.eventId)
                .userId(this.userId)
                .clubId(this.clubId)
                .scope(this.scope)
                .type(this.type)
                .title(this.title)
                .content(this.content)
                .tags(this.tags)
                .images(this.images)
                .location(this.location)
                .capacity(this.capacity)
                .currentParticipants(this.currentParticipants)
                .startsAt(this.startsAt)
                .endsAt(this.endsAt)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
