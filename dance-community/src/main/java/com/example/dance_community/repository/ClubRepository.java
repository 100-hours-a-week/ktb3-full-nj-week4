package com.example.dance_community.repository;

import com.example.dance_community.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    @Modifying
    @Query("update Club c set c.isDeleted = true where c.clubId = :clubId")
    int softDeleteById(@Param("clubId") Long clubId);
}