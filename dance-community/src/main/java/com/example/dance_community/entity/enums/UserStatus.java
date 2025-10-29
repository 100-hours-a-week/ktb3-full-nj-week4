package com.example.dance_community.entity.enums;

public enum UserStatus {
    WAITING("대기중"),
    REJECTED("거절됨"),
    ACTIVE("활동중"),
    LEFT("탈퇴됨"),
    BANNED("정지됨");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}