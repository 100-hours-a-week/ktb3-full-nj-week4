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
    private final Scope scope;
    private final EventType type;

    private final String title;
    private final String content;

    private final List<String> tags;
    private final List<String> images;

    private final Location location;

    private final Long eventId;
    private final Long userId;
    private final Long clubId;

    private final Long capacity;
    private final Long currentParticipants;

    private final LocalDateTime startsAt;
    private final LocalDateTime endsAt;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}