package com.example.dance_community.dto.event;

import com.example.dance_community.dto.post.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EventsResponse {
    private String message;
    private List<EventDto> data;
}
