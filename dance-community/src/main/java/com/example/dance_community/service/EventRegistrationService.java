package com.example.dance_community.service;

import com.example.dance_community.dto.event.EventDto;
import com.example.dance_community.dto.eventRegistration.EventRegistrationDto;
import com.example.dance_community.dto.eventRegistration.EventRegistrationRequest;
import com.example.dance_community.enums.EventRegistrationStatus;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.EventRegistrationRepository;
import com.example.dance_community.repository.EventRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class EventRegistrationService {
    private final EventRegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    public EventRegistrationService(EventRegistrationRepository registrationRepository,
                                    EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
    }

    public EventRegistrationDto register(EventRegistrationRequest request) {
        EventDto event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new NotFoundException("행사 조회 실패"));

        boolean alreadyRegistered = registrationRepository.findByEventIdAndUserId(
                        request.getEventId(), request.getUserId())
                .filter(r -> r.getStatus() == EventRegistrationStatus.SUCCESS)
                .isPresent();
        if (alreadyRegistered) {
            throw new ConflictException("이미 신청한 행사입니다.");
        }

        try {
            EventRegistrationDto newRegistration = EventRegistrationDto.builder()
                    .eventId(request.getEventId())
                    .userId(request.getUserId())
                    .status(EventRegistrationStatus.SUCCESS)
                    .build();
            EventRegistrationDto savedRegistration = registrationRepository.saveRegistration(newRegistration);

            event.incrementParticipants();
            eventRepository.saveEvent(event);

            return savedRegistration;

        } catch (IllegalStateException e) {
            throw new ConflictException("행사 정원 초과");
        } catch (Exception e) {
            throw new RuntimeException("행사 신청 처리 실패");
        }
    }

    public EventRegistrationDto getRegistration(Long eventId, Long userId) {
        return registrationRepository.findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("행사 신청 조회 실패"));
    }

    public List<EventRegistrationDto> getAllRegistrations(Long eventId) {
        try {
            return registrationRepository.findByEventId(eventId).stream()
                    .filter(r -> r.getStatus() == EventRegistrationStatus.SUCCESS)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("행사 신청 인원 조회 실패");
        }
    }

    public EventRegistrationDto cancelRegistration(Long eventId, Long userId) {
        EventRegistrationDto registration = registrationRepository.findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("취소할 신청이 존재하지 않습니다."));

        if (registration.getStatus() == EventRegistrationStatus.FAILED) {
            throw new ConflictException("이미 취소하신 상태입니다.");
        }

        EventDto event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사를 찾을 수 없습니다."));

        EventRegistrationDto canceledRegistration = registration.toBuilder()
                .status(EventRegistrationStatus.FAILED)
                .build();
        EventRegistrationDto savedRegistration = registrationRepository.saveRegistration(canceledRegistration);

        event.decrementParticipants();
        eventRepository.saveEvent(event);

        return savedRegistration;
    }
}
