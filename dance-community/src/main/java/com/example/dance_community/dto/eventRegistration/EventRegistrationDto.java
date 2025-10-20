package com.example.dance_community.dto.eventRegistration;

import com.example.dance_community.enums.EventRegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class EventRegistrationDto {
    private Long eventId;
    private Long userId;

    private EventRegistrationStatus status;

    private LocalDateTime createdAt;

    public EventRegistrationDto() {

    }
}

