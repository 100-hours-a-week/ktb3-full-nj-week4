package com.example.dance_community.dto.eventRegistration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventRegistrationRequest {
    private Long eventId;
    private Long userId;
}
