package com.example.dance_community.dto.event;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class EventsResponse {
    private String message;
    private List<EventDto> data;
}
