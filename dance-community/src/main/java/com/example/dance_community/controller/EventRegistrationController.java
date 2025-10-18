package com.example.dance_community.controller;

import com.example.dance_community.dto.eventRegistration.EventRegistrationDto;
import com.example.dance_community.dto.eventRegistration.EventRegistrationRequest;
import com.example.dance_community.dto.eventRegistration.EventRegistrationResponse;
import com.example.dance_community.dto.eventRegistration.EventRegistrationsResponse;
import com.example.dance_community.service.EventRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventRegistrationController {

    private final EventRegistrationService service;

    public EventRegistrationController(EventRegistrationService service) {
        this.service = service;
    }

    @PostMapping("/registrations")
    public ResponseEntity<EventRegistrationResponse> register(@RequestBody EventRegistrationRequest eventRegistrationRequest) {
        EventRegistrationDto registrationDto = service.register(eventRegistrationRequest);
        return ResponseEntity.status(201).body(new EventRegistrationResponse("행사 신청 성공", registrationDto));
    }

    @GetMapping("/{eventId}/registrations/{userId}")
    public ResponseEntity<EventRegistrationResponse> getRegistration(@PathVariable Long eventId, @PathVariable Long userId) {
        EventRegistrationDto registrationDto = service.getRegistration(eventId, userId);
        return ResponseEntity.ok(new EventRegistrationResponse("행사 신청 조회 성공", registrationDto));
    }

    @GetMapping("/{eventId}/registrations")
    public ResponseEntity<EventRegistrationsResponse> getAllRegistrations(@PathVariable Long eventId) {
        List<EventRegistrationDto> registrationsDto = (List<EventRegistrationDto>) service.getAllRegistrations(eventId);
        return ResponseEntity.ok(new EventRegistrationsResponse("행사 신청 인원 조회 성공", registrationsDto));
    }

    @DeleteMapping("/{eventId}/registrations/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelRegistration(@PathVariable Long eventId, @PathVariable Long userId) {
        service.cancelRegistration(eventId, userId);
    }
}