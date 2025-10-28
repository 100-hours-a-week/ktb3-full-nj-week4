package com.example.dance_community.repository.in_memory;

import com.example.dance_community.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepo {
    Event saveEvent(Event event);
    Optional<Event> findById(Long eventId);
    List<Event> findAll();
    void deleteById(Long id);
}