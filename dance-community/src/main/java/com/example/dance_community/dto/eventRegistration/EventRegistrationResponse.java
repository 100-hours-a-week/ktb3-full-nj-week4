package com.example.dance_community.dto.eventRegistration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventRegistrationResponse {
    private String message;
    private EventRegistrationDto data;
}
