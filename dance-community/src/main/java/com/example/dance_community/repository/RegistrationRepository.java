package com.example.dance_community.repository;

import com.example.dance_community.dto.registration.RegistrationDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RegistrationRepository {
    private final Map<Long, Map<Long, RegistrationDto>> userRegistrationMap = new ConcurrentHashMap<>();
    private final Map<Long, Map<Long, RegistrationDto>> eventRegistrationMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void initData() {
        RegistrationDto newRegistration = RegistrationDto.builder()
                .eventId(1L)
                .userId(1L)
                .isSuccess(true)
                .createdAt(LocalDateTime.now())
                .build();

        this.saveRegistration(newRegistration);
    }

    public RegistrationDto saveRegistration(RegistrationDto dto) {
        userRegistrationMap.computeIfAbsent(dto.getUserId(), k -> new ConcurrentHashMap<>())
                .put(dto.getEventId(), dto);
        eventRegistrationMap.computeIfAbsent(dto.getEventId(), k -> new ConcurrentHashMap<>())
                .put(dto.getUserId(), dto);
        return dto;
    }

    public Optional<RegistrationDto> findRegistration(Long userId, Long eventId) {
        Map<Long, RegistrationDto> map = userRegistrationMap.get(userId);
        return Optional.ofNullable(map.get(eventId));
    }

    public List<RegistrationDto> findEventsByUser(Long userId) {
        Collection<RegistrationDto> values = userRegistrationMap.getOrDefault(userId, Collections.emptyMap()).values();
        return new ArrayList<>(values);
    }

    public List<RegistrationDto> findUsersByEvent(Long eventId) {
        Collection<RegistrationDto> values = eventRegistrationMap.getOrDefault(eventId, Collections.emptyMap()).values();
        return new ArrayList<>(values);
    }
}