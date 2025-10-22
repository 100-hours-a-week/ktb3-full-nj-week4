package com.example.dance_community.controller;

import com.example.dance_community.dto.ApiResponse;
import com.example.dance_community.dto.event.EventDto;
import com.example.dance_community.dto.event.EventRequest;
import com.example.dance_community.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "4_Event", description = "행사 관련 API")
public class EventController {
    private final EventService eventService;

    @Operation(summary = "행사 생성", description = "행사를 새로 만듭니다.")
    @PostMapping()
    public ResponseEntity<ApiResponse<EventDto>> createEvent(HttpServletRequest request, @Valid @RequestBody EventRequest eventRequest) {
        EventDto eventDto = eventService.createEvent(Long.valueOf((String) request.getAttribute("userId")), eventRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("행사 생성 성공", eventDto));
    }

    @Operation(summary = "행사 조회", description = "행사 id를 통해 정보를 불러옵니다.")
    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventDto>> getEvent(@PathVariable Long eventId) {
        EventDto eventDto = eventService.getEvent(eventId);
        return ResponseEntity.ok(new ApiResponse<>("행사 조회 성공", eventDto));
    }

    @Operation(summary = "전체 행사 조회", description = "전체 행사 정보를 불러옵니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EventDto>>> getEvents() {
        List<EventDto> eventDtoList = eventService.getEvents();
        return ResponseEntity.ok(new ApiResponse<>("행사 전체 조회 성공", eventDtoList));
    }

    @Operation(summary = "내 행사 수정", description = "사용자의 행사 정보를 수정합니다.")
    @PatchMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventDto>> updateEvent(@PathVariable Long eventId, @Valid @RequestBody EventRequest eventRequest) {
        EventDto eventDto = eventService.updateEvent(eventId, eventRequest);
        return ResponseEntity.ok(new ApiResponse<>("행사 수정 성공", eventDto));
    }

    @Operation(summary = "행사 삭제", description = "행사 id를 통해 정보를 삭제합니다.")
    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}
