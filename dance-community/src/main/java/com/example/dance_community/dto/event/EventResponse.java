package com.example.dance_community.dto.event;

import com.example.dance_community.enums.EventType;
import com.example.dance_community.enums.Scope;

import java.time.LocalDateTime;
import java.util.List;

public record EventResponse(
        // Identifiers(수정 불가)
        Long eventId,
        Long userId,

        // Classification(수정 불가)
        Scope scope,
        Long clubId,
        EventType type,

        // Details
        String title,
        String content,
        List<String>tags,
        List<String> images,

        // Location
        String locationName,
        String locationAddress,
        String locationLink,

        // Capacity
        Long capacity,
        Long currentParticipants,

        // Schedule
        LocalDateTime startsAt,
        LocalDateTime endsAt,

        // Timestamps
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static EventResponse from(com.example.dance_community.entity.Event entity) {
        return new EventResponse(
                entity.getEventId(),
                entity.getUserId(),
                entity.getScope(),
                entity.getClubId(),
                entity.getType(),
                entity.getTitle(),
                entity.getContent(),
                entity.getTags(),
                entity.getImages(),
                entity.getLocationName(),
                entity.getLocationAddress(),
                entity.getLocationLink(),
                entity.getCapacity(),
                entity.getCurrentParticipants(),
                entity.getStartsAt(),
                entity.getEndsAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
