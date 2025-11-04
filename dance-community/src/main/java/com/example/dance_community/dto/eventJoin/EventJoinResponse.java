package com.example.dance_community.dto.eventJoin;

import com.example.dance_community.entity.EventJoin;

import java.time.LocalDateTime;

public record EventJoinResponse(
        Long eventJoinId,
        Long userId,
        String userName,
        Long eventId,
        String status,
        LocalDateTime createdAt
) {
    public static EventJoinResponse from(EventJoin eventJoin) {
        return new EventJoinResponse(
                eventJoin.getEventJoinId(),
                eventJoin.getParticipant().getUserId(),
                eventJoin.getParticipant().getUsername(),
                eventJoin.getEvent().getEventId(),
                eventJoin.getStatus().name(),
                eventJoin.getCreatedAt()
        );
    }
}
