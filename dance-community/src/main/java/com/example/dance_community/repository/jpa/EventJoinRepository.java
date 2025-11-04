package com.example.dance_community.repository.jpa;

import com.example.dance_community.entity.EventJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventJoinRepository extends JpaRepository<EventJoin, Long> {
}