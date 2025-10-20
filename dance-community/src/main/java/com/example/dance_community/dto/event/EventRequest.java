package com.example.dance_community.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class EventRequest {
    private String scope;
    private String type;

    private String title;
    private String content;

    private List<String> tags;
    private List<String> images;

    private Location location;

    private Long clubId;
    private Long capacity;

    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
}
