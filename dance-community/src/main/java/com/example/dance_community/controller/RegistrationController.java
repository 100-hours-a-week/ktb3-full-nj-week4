package com.example.dance_community.controller;

import com.example.dance_community.dto.registration.RegistrationDto;
import com.example.dance_community.dto.registration.RegistrationResponse;
import com.example.dance_community.dto.registration.RegistrationsResponse;
import com.example.dance_community.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService service;

    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<RegistrationResponse> register(HttpServletRequest request, @PathVariable Long eventId) {
        RegistrationDto registrationDto = service.createRegistration(Long.valueOf((String) request.getAttribute("userId")), eventId);
        return ResponseEntity.status(201).body(new RegistrationResponse("행사 신청 성공", registrationDto));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<RegistrationResponse> getRegistration(HttpServletRequest request, @PathVariable Long eventId) {
        RegistrationDto registrationDto = service.getRegistration(Long.valueOf((String) request.getAttribute("userId")), eventId);
        return ResponseEntity.ok(new RegistrationResponse("행사 신청 조회 성공", registrationDto));
    }

    @GetMapping("/{eventId}/all")
    public ResponseEntity<RegistrationsResponse> getAllUserRegistrations(@PathVariable Long eventId) {
        List<RegistrationDto> registrationsDto = (List<RegistrationDto>) service.getAllUserRegistrations(eventId);
        return ResponseEntity.ok(new RegistrationsResponse("행사 신청 인원 조회 성공", registrationsDto));
    }

    @GetMapping("/all")
    public ResponseEntity<RegistrationsResponse> getAllEventRegistrations(HttpServletRequest request) {
        List<RegistrationDto> registrationsDto = (List<RegistrationDto>) service.getAllEventRegistrations(Long.valueOf((String) request.getAttribute("userId")));
        return ResponseEntity.ok(new RegistrationsResponse("신청한 행사 내역 조회 성공", registrationsDto));
    }

    @PatchMapping("/{eventId}/cancel")
    public ResponseEntity<RegistrationResponse> cancelRegistration(HttpServletRequest request, @PathVariable Long eventId) {
        RegistrationDto registrationsDto = service.cancelRegistration(Long.valueOf((String) request.getAttribute("userId")), eventId);
        return ResponseEntity.ok(new RegistrationResponse("행사 신청 취소 성공", registrationsDto));
    }
}