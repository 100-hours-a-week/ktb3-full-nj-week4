package com.example.dance_community.dto.eventJoin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class EventJoinDto {
    private Long eventId;
    private Long userId;

    private boolean isSuccess;

    private LocalDateTime createdAt;
}