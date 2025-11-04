package com.example.dance_community.repository.in_memory;

import com.example.dance_community.dto.eventJoin.EventJoinDto;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepo {
    EventJoinDto saveRegistration(EventJoinDto dto);
    Optional<EventJoinDto> findRegistration(Long userId, Long eventId);
    List<EventJoinDto> findEventsByUser(Long userId);
    List<EventJoinDto> findUsersByEvent(Long eventId);
}
