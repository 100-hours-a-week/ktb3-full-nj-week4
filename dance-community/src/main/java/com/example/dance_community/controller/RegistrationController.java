package com.example.dance_community.controller;

import com.example.dance_community.auth.GetUserId;
import com.example.dance_community.dto.ApiResponse;
import com.example.dance_community.dto.eventJoin.EventJoinDto;
import com.example.dance_community.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@Tag(name = "6_Registration", description = "행사 신청 관련 API")
public class RegistrationController {

    private final RegistrationService service;

    @Operation(summary = "행사 신청", description = "행사 id를 통해 행사에 신청합니다.")
    @PostMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventJoinDto>> register(@GetUserId Long userId, @PathVariable Long eventId) {
        EventJoinDto eventJoinDto = service.createRegistration(userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("행사 신청 성공", eventJoinDto));
    }

    @Operation(summary = "행사 신청 조회", description = "회원 id와 행사 id를 통해 행사 신청 정보를 불러옵니다.")
    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventJoinDto>> getRegistration(@GetUserId Long userId, @PathVariable Long eventId) {
        EventJoinDto eventJoinDto = service.getRegistration(userId, eventId);
        return ResponseEntity.ok(new ApiResponse<>("행사 신청 조회 성공", eventJoinDto));
    }

    @Operation(summary = "행사 신청 인원 조회", description = "행사 id를 통해 행사에 신청한 인원 정보를 불러옵니다.")
    @GetMapping("/{eventId}/all")
    public ResponseEntity<ApiResponse<List<EventJoinDto>>> getAllUserRegistrations(@PathVariable Long eventId) {
        List<EventJoinDto> registrationsDto = (List<EventJoinDto>) service.getAllUserRegistrations(eventId);
        return ResponseEntity.ok(new ApiResponse<>("행사 신청 인원 조회 성공", registrationsDto));
    }

    @Operation(summary = "내 신청 조회", description = "사용자가 신청한 행사 신청 정보를 불러옵니다.")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<EventJoinDto>>> getAllEventRegistrations(@GetUserId Long userId) {
        List<EventJoinDto> registrationsDto = (List<EventJoinDto>) service.getAllEventRegistrations(userId);
        return ResponseEntity.ok(new ApiResponse<>("신청한 행사 내역 조회 성공", registrationsDto));
    }

    @Operation(summary = "행사 신청 취소", description = "행사 id를 통해 행사 신청을 취소합니다.")
    @PatchMapping("/{eventId}/cancel")
    public ResponseEntity<Optional> cancelRegistration(@GetUserId Long userId, @PathVariable Long eventId) {
        EventJoinDto registrationsDto = service.cancelRegistration(userId, eventId);
        return ResponseEntity.noContent().build();
    }
}