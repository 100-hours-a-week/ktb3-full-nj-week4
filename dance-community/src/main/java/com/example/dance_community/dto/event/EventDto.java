package com.example.dance_community.dto.event;

import com.example.dance_community.enums.Scope;
import com.example.dance_community.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class EventDto {
    private Scope scope;
    private EventType type;

    private String title;
    private String content;

    private List<String> tags;
    private List<String> images;

    private Location location;

    private Long eventId;
    private Long userId;
    private Long clubId;

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
}

