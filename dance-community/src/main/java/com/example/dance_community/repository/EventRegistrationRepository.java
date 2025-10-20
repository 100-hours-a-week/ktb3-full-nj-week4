package com.example.dance_community.repository;

import com.example.dance_community.dto.eventRegistration.EventRegistrationDto;
import com.example.dance_community.enums.EventRegistrationStatus;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EventRegistrationRepository {
    private final Map<Long, Map<Long, EventRegistrationDto>> eventRegistrationMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void initData() {
        EventRegistrationDto newRegistration = EventRegistrationDto.builder()
                .eventId(1L)
                .userId(1L)
                .status(EventRegistrationStatus.SUCCESS)
                .createdAt(LocalDateTime.now())
                .build();

        this.saveRegistration(newRegistration);
    }

    public EventRegistrationDto saveRegistration(EventRegistrationDto dto) {
        eventRegistrationMap.computeIfAbsent(dto.getEventId(), k -> new ConcurrentHashMap<>())
                .put(dto.getUserId(), dto);
        return dto;
    }

    public Optional<EventRegistrationDto> findByEventIdAndUserId(Long eventId, Long userId) {
        Map<Long, EventRegistrationDto> map = eventRegistrationMap.get(eventId);
        return Optional.ofNullable(map.get(userId));
    }

    public List<EventRegistrationDto> findByEventId(Long eventId) {
        Collection<EventRegistrationDto> values = eventRegistrationMap.getOrDefault(eventId, Collections.emptyMap()).values();
        return new ArrayList<>(values);
    }
}