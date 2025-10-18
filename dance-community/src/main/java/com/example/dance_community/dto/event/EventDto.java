package com.example.dance_community.dto.event;

import com.example.dance_community.enums.Scope;
import com.example.dance_community.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
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

    public EventDto() {

    }
}

