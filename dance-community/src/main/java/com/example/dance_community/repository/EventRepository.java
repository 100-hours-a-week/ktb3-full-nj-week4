package com.example.dance_community.repository;

import com.example.dance_community.dto.event.EventDto;
import com.example.dance_community.dto.event.Location;
import com.example.dance_community.enums.EventType;
import com.example.dance_community.enums.Scope;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepository{
    private final Map<Long, EventDto> eventMap = new ConcurrentHashMap<>();
    private final AtomicLong eventId = new AtomicLong(0);

    @PostConstruct
    public void initData() {
        EventDto defaultEvent = new EventDto();
        defaultEvent.setEventId(eventId.incrementAndGet());
        defaultEvent.setUserId(1L);
        defaultEvent.setScope(Scope.GLOBAL);
        defaultEvent.setClubId(null);
        defaultEvent.setType(EventType.WORKSHOP);
        defaultEvent.setTitle("Party Lock");
        defaultEvent.setContent("대학씬 최고 행사 파티락이 열립니다!");
        defaultEvent.setStartsAt(LocalDateTime.now());
        defaultEvent.setEndsAt(LocalDateTime.now());
        Location location = new Location();
        location.setName("스테핀 스튜디오");
        location.setAddress("서울 서초구 방배천로6길 3-4 B1층");
        location.setLink("https://naver.me/GdyG7ZUn");
        defaultEvent.setLocation(location);
        defaultEvent.setCapacity(30L);
        defaultEvent.setCurrentParticipants(29L);
        defaultEvent.setTags(Arrays.asList("locking", "beginner"));
        defaultEvent.setImages(Arrays.asList("https://image3.kr/img.jpg", "https://image4.kr/img.jpg"));
        defaultEvent.setCreatedAt(LocalDateTime.now());
        eventMap.put(defaultEvent.getEventId(), defaultEvent);
    }

    public EventDto saveEvent(EventDto eventDto) {
        if (eventDto.getEventId() == null) {
            eventDto.setEventId(eventId.incrementAndGet());
            eventDto.setCreatedAt(LocalDateTime.now());
        }
        eventMap.put(eventDto.getEventId(), eventDto);
        return eventDto;
    }

    public Optional<EventDto> findById(Long eventId) {
        return Optional.ofNullable(eventMap.get(eventId));
    }

    public List<EventDto> findAll() {
        return new ArrayList<>(eventMap.values());
    }

    public void deleteById(Long id) {
        eventMap.remove(id);
    }
}
