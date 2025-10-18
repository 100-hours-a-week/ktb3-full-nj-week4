package com.example.dance_community.service;

import com.example.dance_community.dto.event.EventDto;
import com.example.dance_community.dto.event.EventRequest;
import com.example.dance_community.enums.EventType;
import com.example.dance_community.enums.Scope;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventDto createEvent(Long userId, EventRequest eventRequest) {
        if (eventRequest.getScope() == null || eventRequest.getType() == null ||
                eventRequest.getTitle() == null || eventRequest.getContent() == null ||
                eventRequest.getStartsAt() == null || eventRequest.getEndsAt() == null ||
                eventRequest.getLocation() == null || eventRequest.getCapacity() == null) {
            throw new InvalidRequestException("필수 필드 누락");
        }

        EventDto eventDto = new EventDto();
        eventDto.setUserId(userId);
        eventDto.setScope(Scope.valueOf(eventRequest.getScope()));
        eventDto.setType(EventType.valueOf(eventRequest.getType()));
        eventDto.setClubId(eventRequest.getClubId());
        eventDto.setTitle(eventRequest.getTitle());
        eventDto.setContent(eventRequest.getContent());
        eventDto.setTags(eventRequest.getTags());
        eventDto.setImages(eventRequest.getImages());
        eventDto.setLocation(eventRequest.getLocation());
        eventDto.setCapacity(eventRequest.getCapacity());
        eventDto.setStartsAt(eventRequest.getStartsAt());
        eventDto.setEndsAt(eventRequest.getEndsAt());

        EventDto newEvent = eventRepository.saveEvent(eventDto);
        return newEvent;
    }

    public EventDto getEvent(Long eventId) {
        EventDto eventDto = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 조회 실패"));
        return eventDto;
    }

    public List<EventDto> getEvents() {
        try {
            return eventRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("행사 전체 조회 실패");
        }
    }

    public EventDto updateEvent(Long eventId, EventRequest eventRequest) {
        EventDto eventDto = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 조회 실패"));

        try {
            if (eventRequest.getScope() != null) eventDto.setScope(Scope.valueOf(eventRequest.getScope()));
            if (eventRequest.getType() != null) eventDto.setType(EventType.valueOf(eventRequest.getType()));
            if (eventRequest.getTitle() != null) eventDto.setTitle(eventRequest.getTitle());
            if (eventRequest.getContent() != null) eventDto.setContent(eventRequest.getContent());
            if (eventRequest.getTags() != null) eventDto.setTags(eventRequest.getTags());
            if (eventRequest.getImages() != null) eventDto.setImages(eventRequest.getImages());
            if (eventRequest.getLocation() != null) eventDto.setLocation(eventRequest.getLocation());
            if (eventRequest.getCapacity() != null) eventDto.setCapacity(eventRequest.getCapacity());
            if (eventRequest.getStartsAt() != null) eventDto.setStartsAt(eventRequest.getStartsAt());
            if (eventRequest.getEndsAt() != null) eventDto.setEndsAt(eventRequest.getEndsAt());
        } catch (Exception e) {
            throw new InvalidRequestException("잘못된 요청 데이터");
        }
        eventDto.setUpdatedAt(LocalDateTime.now());
        eventRepository.saveEvent(eventDto);

        return eventDto;
    }

    public void deleteEvent(Long eventId) {
        EventDto eventDto = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 삭제 실패"));

        eventRepository.deleteById(eventId);
    }
}
