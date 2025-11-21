package com.example.dance_community.repository.jpa;

import com.example.dance_community.entity.Club;
import com.example.dance_community.entity.ClubJoin;
import com.example.dance_community.entity.User;
import com.example.dance_community.entity.enums.ClubJoinStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubJoinRepository extends JpaRepository<ClubJoin, Long> {
    List<ClubJoin> findByClub(Club club);

    List<ClubJoin> findByUser(User user);
    List<ClubJoin> findByUser_UserIdAndStatus(Long userId, ClubJoinStatus status);

    boolean existsByUserAndClub(User user, Club club);

    void deleteByUserAndClub(User user, Club club);
}