package com.example.dance_community.dto.eventRegistration;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EventRegistrationsResponse {
    private String message;
    private List<EventRegistrationDto> data;
}
