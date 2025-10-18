package com.example.dance_community.dto.eventRegistration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRegistrationResponse {
    private String message;
    private EventRegistrationDto data;
}
