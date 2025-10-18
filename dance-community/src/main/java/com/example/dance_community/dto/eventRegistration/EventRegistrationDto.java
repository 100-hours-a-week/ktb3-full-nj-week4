package com.example.dance_community.dto.eventRegistration;

import com.example.dance_community.enums.EventRegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventRegistrationDto {
    private Long eventId;
    private Long userId;

    private EventRegistrationStatus status;

    private LocalDateTime createdAt;

    public EventRegistrationDto() {

    }
}

