package com.example.dance_community.repository.jpa;

import com.example.dance_community.entity.ClubJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubJoinRepository extends JpaRepository<ClubJoin, Long> {
}