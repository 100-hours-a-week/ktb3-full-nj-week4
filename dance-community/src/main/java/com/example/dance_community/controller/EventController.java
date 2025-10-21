package com.example.dance_community.controller;

import com.example.dance_community.dto.event.EventDto;
import com.example.dance_community.dto.event.EventRequest;
import com.example.dance_community.dto.event.EventResponse;
import com.example.dance_community.dto.event.EventsResponse;
import com.example.dance_community.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping()
    public ResponseEntity<EventResponse> createEvent(HttpServletRequest request, @Valid @RequestBody EventRequest eventRequest) {
        EventDto eventDto = eventService.createEvent(Long.valueOf((String) request.getAttribute("userId")), eventRequest);
        return ResponseEntity.status(201).body(new EventResponse("행사 생성 성공", eventDto));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable Long eventId) {
        EventDto eventDto = eventService.getEvent(eventId);
        return ResponseEntity.ok(new EventResponse("행사 조회 성공", eventDto));
    }

    @GetMapping
    public ResponseEntity<EventsResponse> getEvents() {
        List<EventDto> eventDtos = eventService.getEvents();
        return ResponseEntity.ok(new EventsResponse("행사 전체 조회 성공", eventDtos));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long eventId, @Valid @RequestBody EventRequest eventRequest) {
        EventDto eventDto = eventService.updateEvent(eventId, eventRequest);
        return ResponseEntity.ok(new EventResponse("행사 수정 성공", eventDto));
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
    }
}
