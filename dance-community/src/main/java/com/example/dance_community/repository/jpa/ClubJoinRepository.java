package com.example.dance_community.repository.jpa;

import com.example.dance_community.entity.Club;
import com.example.dance_community.entity.ClubJoin;
import com.example.dance_community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubJoinRepository extends JpaRepository<ClubJoin, Long> {
    List<ClubJoin> findByClub(Club club);

    List<ClubJoin> findByUser(User user);

    boolean existsByClubAndUser(User user, Club club);

    void deleteByClubAndUser(User user, Club club);
}