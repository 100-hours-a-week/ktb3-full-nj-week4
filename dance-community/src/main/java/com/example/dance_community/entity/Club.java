package com.example.dance_community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "clubs")
public class Club extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @Column(nullable = false, unique = true, length = 100)
    private String clubName;

    @Column(length = 1000)
    private String description;

    @Builder
    public Club(String clubName, String description) {
        if (clubName == null || clubName.isBlank()) {
            throw new IllegalArgumentException("클럽 이름 미입력");
        }

        this.clubName = clubName;
        this.description = description;
    }

    public Club updateClub(String description) {
        this.description = description;
        return this;
    }

    private void checkNullOrBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName+" 미입력");
        }
    }
}