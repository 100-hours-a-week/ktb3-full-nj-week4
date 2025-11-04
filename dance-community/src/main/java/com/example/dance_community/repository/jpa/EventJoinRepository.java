package com.example.dance_community.repository.jpa;

import com.example.dance_community.entity.Event;
import com.example.dance_community.entity.EventJoin;
import com.example.dance_community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventJoinRepository extends JpaRepository<EventJoin, Long> {
    List<EventJoin> findByEvent(Event event);

    List<EventJoin> findByParticipant(User user);

    boolean existsByEventAndParticipant(User user, Event event);

    void deleteByEventAndParticipant(User user, Event event);
}