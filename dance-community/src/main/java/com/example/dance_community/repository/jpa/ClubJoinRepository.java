package com.example.dance_community.repository.jpa;

import com.example.dance_community.entity.Club;
import com.example.dance_community.entity.ClubJoin;
import com.example.dance_community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubJoinRepository extends JpaRepository<ClubJoin, Long> {
    // 특정 클럽의 모든 멤버 조회
    List<ClubJoin> findByClub(Club club);

    // 특정 유저가 가입한 모든 클럽 조회
    List<ClubJoin> findByUser(User user);

    // 중복 가입 체크
    boolean existsByClubAndUser(User user, Club club);

    // 멤버십 삭제 (탈퇴)
    void deleteByClubAndUser(User user, Club club);
}