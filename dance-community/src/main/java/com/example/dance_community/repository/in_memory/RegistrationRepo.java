package com.example.dance_community.repository.in_memory;

import com.example.dance_community.dto.registration.RegistrationDto;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepo {
    RegistrationDto saveRegistration(RegistrationDto dto);
    Optional<RegistrationDto> findRegistration(Long userId, Long eventId);
    List<RegistrationDto> findEventsByUser(Long userId);
    List<RegistrationDto> findUsersByEvent(Long eventId);
}
