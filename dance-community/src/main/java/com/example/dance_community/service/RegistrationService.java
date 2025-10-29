package com.example.dance_community.service;

import com.example.dance_community.dto.registration.RegistrationDto;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.RegistrationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {
    private final RegistrationRepo registrationRepo;
    private final EventService eventService;

    public RegistrationService(RegistrationRepo registrationRepo, EventService eventService) {
        this.registrationRepo = registrationRepo;
        this.eventService = eventService;
    }

    public RegistrationDto createRegistration(Long userId, Long eventId) {
        eventService.validateCanRegister(eventId);

        boolean alreadyRegistered = registrationRepo
                .findRegistration(eventId, userId)
                .filter(r -> r.isSuccess())
                .isPresent();
        if (alreadyRegistered) {
            throw new ConflictException("중복 신청 거부");
        }

        RegistrationDto newRegistration = RegistrationDto.builder()
                .eventId(eventId)
                .userId(userId)
                .isSuccess(true)
                .build();
        RegistrationDto saved = registrationRepo.saveRegistration(newRegistration);

        eventService.incrementParticipants(eventId);
        return saved;
    }

    public RegistrationDto getRegistration(Long userId, Long eventId) {
        return registrationRepo.findRegistration(userId, eventId)
                .orElseThrow(() -> new NotFoundException("행사 신청 조회 실패"));
    }

    public List<RegistrationDto> getAllEventRegistrations(Long userId) {
        try {
            return registrationRepo.findEventsByUser(userId).stream()
                    .filter(r -> r.isSuccess())
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("신청한 행사 내역 조회 실패");
        }
    }

    public List<RegistrationDto> getAllUserRegistrations(Long eventId) {
        try {
            return registrationRepo.findUsersByEvent(eventId).stream()
                    .filter(r -> r.isSuccess())
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("행사 신청 인원 조회 실패");
        }
    }

    public RegistrationDto cancelRegistration(Long userId, Long eventId) {
        RegistrationDto registration = registrationRepo.findRegistration(userId, eventId)
                .orElseThrow(() -> new NotFoundException("행사 신청 조회 실패"));

        if (!registration.isSuccess()) {
            throw new ConflictException("이미 취소 완료");
        }

        eventService.decrementParticipants(eventId);

        RegistrationDto canceledRegistration = registration.toBuilder()
                .isSuccess(false)
                .build();
        return registrationRepo.saveRegistration(canceledRegistration);
    }
}
