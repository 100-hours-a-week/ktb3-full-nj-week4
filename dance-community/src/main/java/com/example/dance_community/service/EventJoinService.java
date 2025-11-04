package com.example.dance_community.service;

import com.example.dance_community.dto.eventJoin.EventJoinDto;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.in_memory.RegistrationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventJoinService {
    private final RegistrationRepo registrationRepo;
    private final EventService eventService;

    public EventJoinService(RegistrationRepo registrationRepo, EventService eventService) {
        this.registrationRepo = registrationRepo;
        this.eventService = eventService;
    }

    public EventJoinDto createRegistration(Long userId, Long eventId) {
        eventService.validateCanRegister(eventId);

        boolean alreadyRegistered = registrationRepo
                .findRegistration(eventId, userId)
                .filter(r -> r.isSuccess())
                .isPresent();
        if (alreadyRegistered) {
            throw new ConflictException("중복 신청 거부");
        }

        EventJoinDto newRegistration = EventJoinDto.builder()
                .eventId(eventId)
                .userId(userId)
                .isSuccess(true)
                .build();
        EventJoinDto saved = registrationRepo.saveRegistration(newRegistration);

        eventService.incrementParticipants(eventId);
        return saved;
    }

    public EventJoinDto getRegistration(Long userId, Long eventId) {
        return registrationRepo.findRegistration(userId, eventId)
                .orElseThrow(() -> new NotFoundException("행사 신청 조회 실패"));
    }

    public List<EventJoinDto> getAllEventRegistrations(Long userId) {
        try {
            return registrationRepo.findEventsByUser(userId).stream()
                    .filter(r -> r.isSuccess())
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("신청한 행사 내역 조회 실패");
        }
    }

    public List<EventJoinDto> getAllUserRegistrations(Long eventId) {
        try {
            return registrationRepo.findUsersByEvent(eventId).stream()
                    .filter(r -> r.isSuccess())
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("행사 신청 인원 조회 실패");
        }
    }

    public EventJoinDto cancelRegistration(Long userId, Long eventId) {
        EventJoinDto registration = registrationRepo.findRegistration(userId, eventId)
                .orElseThrow(() -> new NotFoundException("행사 신청 조회 실패"));

        if (!registration.isSuccess()) {
            throw new ConflictException("이미 취소 완료");
        }

        eventService.decrementParticipants(eventId);

        EventJoinDto canceledRegistration = registration.toBuilder()
                .isSuccess(false)
                .build();
        return registrationRepo.saveRegistration(canceledRegistration);
    }
}
