package com.example.dance_community.repository;

import com.example.dance_community.dto.event.EventDto;

import java.util.List;
import java.util.Optional;

public interface EventRepo {
    EventDto saveEvent(EventDto eventDto);
    Optional<EventDto> findById(Long eventId);
    List<EventDto> findAll();
    void deleteById(Long id);
}
