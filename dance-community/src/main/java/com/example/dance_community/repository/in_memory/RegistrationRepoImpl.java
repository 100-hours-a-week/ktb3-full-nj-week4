package com.example.dance_community.repository.in_memory;

import com.example.dance_community.dto.registration.RegistrationDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RegistrationRepoImpl implements RegistrationRepo{
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

    @Override
    public RegistrationDto saveRegistration(RegistrationDto dto) {
        userRegistrationMap.computeIfAbsent(dto.getUserId(), k -> new ConcurrentHashMap<>())
                .put(dto.getEventId(), dto);
        eventRegistrationMap.computeIfAbsent(dto.getEventId(), k -> new ConcurrentHashMap<>())
                .put(dto.getUserId(), dto);
        return dto;
    }

    @Override
    public Optional<RegistrationDto> findRegistration(Long userId, Long eventId) {
        return Optional.ofNullable(
                userRegistrationMap.getOrDefault(userId, Collections.emptyMap()).get(eventId)
        );
    }

    @Override
    public List<RegistrationDto> findEventsByUser(Long userId) {
        Collection<RegistrationDto> values = userRegistrationMap.getOrDefault(userId, Collections.emptyMap()).values();
        return new ArrayList<>(values);
    }

    @Override
    public List<RegistrationDto> findUsersByEvent(Long eventId) {
        Collection<RegistrationDto> values = eventRegistrationMap.getOrDefault(eventId, Collections.emptyMap()).values();
        return new ArrayList<>(values);
    }
}