package com.example.dance_community.dto.eventRegistration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRegistrationRequest {
    private Long eventId;
    private Long userId;
}
