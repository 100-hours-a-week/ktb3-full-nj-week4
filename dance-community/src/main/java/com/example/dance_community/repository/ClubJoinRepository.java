package com.example.dance_community.repository;

import com.example.dance_community.entity.ClubJoin;
import com.example.dance_community.enums.ClubJoinStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubJoinRepository extends JpaRepository<ClubJoin, Long> {
    List<ClubJoin> findByClub_ClubIdAndStatus(Long clubId, ClubJoinStatus status);
    List<ClubJoin> findByUser_UserIdAndStatus(Long userId, ClubJoinStatus status);

    ClubJoin findByUser_UserIdAndClub_ClubId(Long userId, Long clubId);
    boolean existsByUser_UserIdAndClub_ClubId(Long userId, Long clubId);
}