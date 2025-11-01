package com.example.dance_community.repository.in_memory;

import com.example.dance_community.entity.Event;
import com.example.dance_community.entity.enums.EventType;
import com.example.dance_community.entity.enums.Scope;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepoImpl implements EventRepo{
    private final Map<Long, Event> eventMap = new ConcurrentHashMap<>();
    private final AtomicLong eventId = new AtomicLong(0);

    @PostConstruct
    public void initData() {
        Event defaultEvent = Event.builder()
                .eventId(eventId.incrementAndGet())
                .userId(1L)
                .scope(Scope.GLOBAL)
                .clubId(null)
                .type(EventType.WORKSHOP)
                .title("Party Lock")
                .content("대학씬 최고 행사 파티락이 열립니다!")
                .startsAt(LocalDateTime.now())
                .endsAt(LocalDateTime.now())
                .locationName("스테핀 스튜디오")
                .locationAddress("서울 서초구 방배천로6길 3-4 B1층")
                .locationLink("https://naver.me/GdyG7ZUn")
                .capacity(30L)
                .currentParticipants(29L)
                .tags(Arrays.asList("locking", "beginner"))
                .images(Arrays.asList("https://image3.kr/img.jpg", "https://image4.kr/img.jpg"))
                .createdAt(LocalDateTime.now())
                .build();

        this.saveEvent(defaultEvent);
    }

    @Override
    public Event saveEvent(Event event) {
        if (event.getEventId() == null) {
            event.toBuilder().eventId(eventId.incrementAndGet());
        }
        eventMap.put(event.getEventId(), event);
        return event;
    }

    @Override
    public Optional<Event> findById(Long eventId) {
        return Optional.ofNullable(eventMap.get(eventId));
    }

    @Override
    public List<Event> findAll() {
        return new ArrayList<>(eventMap.values());
    }

    @Override
    public void deleteById(Long id) {
        eventMap.remove(id);
    }
}