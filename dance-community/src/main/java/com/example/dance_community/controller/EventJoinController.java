package com.example.dance_community.controller;

import com.example.dance_community.dto.ApiResponse;
import com.example.dance_community.dto.eventJoin.EventJoinCreateRequest;
import com.example.dance_community.dto.eventJoin.EventJoinResponse;
import com.example.dance_community.security.UserDetail;
import com.example.dance_community.service.EventJoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event-joins")
@RequiredArgsConstructor
@Tag(name = "7_EventJoin", description = "행사 신청 관련 API")
public class EventJoinController {
    private final EventJoinService eventJoinService;

    @Operation(summary = "행사 신청", description = "행사에 신청합니다.")
    @PostMapping()
    public ResponseEntity<ApiResponse<EventJoinResponse>> createEventJoin(@AuthenticationPrincipal UserDetail userDetail, @Valid @RequestBody EventJoinCreateRequest eventJoinCreateRequest) {
        EventJoinResponse eventJoinResponse = eventJoinService.createEventJoin(userDetail.getUserId(), eventJoinCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("행사 신청 성공", eventJoinResponse));
    }

    @Operation(summary = "사용자가 신청한 행사 조회", description = "특정 사용자가 신청한 모든 행사 정보를 불러옵니다.")
    @GetMapping("/event")
    public ResponseEntity<ApiResponse<List<EventJoinResponse>>> getUserEvents(@AuthenticationPrincipal UserDetail userDetail) {
        List<EventJoinResponse> eventJoinResponses = eventJoinService.getUserEvents(userDetail.getUserId());
        return ResponseEntity.ok(new ApiResponse<>("사용자가 신청한 행사 조회 성공", eventJoinResponses));
    }

    @Operation(summary = "행사에 신청한 사용자 조회", description = "특정 행사에 신청한 모든 사용자의 정보를 불러옵니다.")
    @GetMapping("/user/{eventId}")
    public ResponseEntity<ApiResponse<List<EventJoinResponse>>> getEventUsers(@PathVariable Long eventId) {
        List<EventJoinResponse> eventJoinResponses = eventJoinService.getEventUsers(eventId);
        return ResponseEntity.ok(new ApiResponse<>("행사에 신청한 사용자 조회 성공", eventJoinResponses));
    }

    @Operation(summary = "행사 신청 확인", description = "특정 사용자가 특정 행사에 신청했는지 확인합니다.")
    @GetMapping("/check/{eventId}")
    public ResponseEntity<Boolean> checkApplication(@AuthenticationPrincipal UserDetail userDetail, @PathVariable Long eventId) {
        boolean isApplied = eventJoinService.isEventJoin(userDetail.getUserId(), eventId);
        return ResponseEntity.ok(isApplied);
    }

    @Operation(summary = "행사 신청 취소", description = "행사 신청을 취소합니다.")
    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteEventJoin(@AuthenticationPrincipal UserDetail userDetail, @PathVariable Long eventId) {
        eventJoinService.deleteEventJoin(userDetail.getUserId(), eventId);
        return ResponseEntity.noContent().build();
    }
}