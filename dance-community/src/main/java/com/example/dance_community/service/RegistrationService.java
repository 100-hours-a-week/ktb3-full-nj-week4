package com.example.dance_community.service;

import com.example.dance_community.dto.event.EventDto;
import com.example.dance_community.dto.registration.RegistrationDto;
import com.example.dance_community.entity.Event;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.EventRepo;
import com.example.dance_community.repository.RegistrationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {
    private final RegistrationRepo registrationRepo;
    private final EventRepo eventRepo;

    public RegistrationService(RegistrationRepo registrationRepo,
                               EventRepo eventRepo) {
        this.registrationRepo = registrationRepo;
        this.eventRepo = eventRepo;
    }

    public RegistrationDto createRegistration(Long userId, Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 조회 실패"));

        boolean alreadyRegistered = registrationRepo.findRegistration(
                        eventId, userId)
                .filter(r -> r.isSuccess())
                .isPresent();
        if (alreadyRegistered) {
            throw new ConflictException("중복 신청 거부");
        }

        try {
            event.incrementParticipants();
            eventRepo.saveEvent(event);

            RegistrationDto newRegistration = RegistrationDto.builder()
                    .eventId(eventId)
                    .userId(userId)
                    .isSuccess(true)
                    .build();
            return registrationRepo.saveRegistration(newRegistration);
        } catch (IllegalStateException e) {
            throw new ConflictException("행사 정원 초과");
        } catch (Exception e) {
            throw new RuntimeException("행사 신청 처리 실패");
        }
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
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 조회 실패"));

        RegistrationDto registration = registrationRepo.findRegistration(userId, eventId)
                .orElseThrow(() -> new NotFoundException("행사 신청 조회 실패"));

        if (!registration.isSuccess()) {
            throw new ConflictException("이미 취소 완료");
        }

        event.decrementParticipants();
        eventRepo.saveEvent(event);

        RegistrationDto canceledRegistration = registration.toBuilder()
                .isSuccess(false)
                .build();
        return registrationRepo.saveRegistration(canceledRegistration);
    }
}
