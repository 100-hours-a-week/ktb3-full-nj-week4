package com.example.dance_community.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventResponse {
    private String message;
    private EventDto data;
}
