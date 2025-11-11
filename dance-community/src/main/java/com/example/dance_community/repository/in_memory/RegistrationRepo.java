package com.example.dance_community.repository.in_memory;

import com.example.dance_community.dto.eventJoin.EventJoinCreateRequest;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepo {
    EventJoinCreateRequest saveRegistration(EventJoinCreateRequest dto);
    Optional<EventJoinCreateRequest> findRegistration(Long userId, Long eventId);
    List<EventJoinCreateRequest> findEventsByUser(Long userId);
    List<EventJoinCreateRequest> findUsersByEvent(Long eventId);
}
