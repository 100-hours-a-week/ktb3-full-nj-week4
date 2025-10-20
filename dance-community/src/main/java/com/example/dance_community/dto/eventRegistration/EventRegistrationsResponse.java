package com.example.dance_community.dto.eventRegistration;

import lombok.AllArgsConstructor;
import java.util.List;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventRegistrationsResponse {
    private String message;
    private List<EventRegistrationDto> data;
}
