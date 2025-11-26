package com.example.dance_community.service;

import com.example.dance_community.dto.eventJoin.EventJoinCreateRequest;
import com.example.dance_community.dto.eventJoin.EventJoinResponse;
import com.example.dance_community.entity.*;
import com.example.dance_community.enums.EventJoinStatus;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.repository.EventJoinRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventJoinService {
    private final EventJoinRepository eventJoinRepository;
    private final UserService userService;
    private final EventService eventService;

    @Transactional
    public EventJoinResponse createEventJoin(Long userId, EventJoinCreateRequest request) {
        User user = userService.findByUserId(userId);
        Event event = eventService.getActiveEvent(request.getEventId());

        if (eventJoinRepository.existsByParticipantAndEvent(user, event)) {
            throw new ConflictException("이미 신청한 행사");
        }

        EventJoin newEventJoin = EventJoin.builder()
                .participant(user)
                .event(event)
                .status(EventJoinStatus.valueOf(request.getStatus()))
                .build();

        EventJoin savedEventJoin = eventJoinRepository.save(newEventJoin);
        return EventJoinResponse.from(savedEventJoin);
    }

    public List<EventJoinResponse> getUserEvents(Long userId) {
        User user = userService.findByUserId(userId);

        List<EventJoin> eventJoins = eventJoinRepository.findByParticipant(user);
        return eventJoins.stream()
                .map(EventJoinResponse::from)
                .toList();
    }

    public List<EventJoinResponse> getEventUsers(Long eventId) {
        Event event = eventService.getActiveEvent(eventId);

        List<EventJoin> eventJoins = eventJoinRepository.findByEvent(event);
        return eventJoins.stream()
                .map(EventJoinResponse::from)
                .toList();
    }

    @Transactional
    public void deleteEventJoin(Long userId, Long eventId) {
        User user = userService.findByUserId(userId);
        Event event = eventService.getActiveEvent(eventId);

        if (!eventJoinRepository.existsByParticipantAndEvent(user, event)) {
            throw new InvalidRequestException("신청하지 않은 행사");
        }

        eventJoinRepository.deleteByParticipantAndEvent(user, event);
    }

    public boolean isEventJoin(Long userId, Long eventId) {
        User user = userService.findByUserId(userId);
        Event event = eventService.getActiveEvent(eventId);

        return eventJoinRepository.existsByParticipantAndEvent(user, event);
    }
}
