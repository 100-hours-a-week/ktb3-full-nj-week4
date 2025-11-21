package com.example.dance_community.enums;

public enum EventJoinStatus {
    CONFIRMED("확인됨"),
    CANCELED("취소됨"),
    REJECTED("거절됨");

    private final String description;

    EventJoinStatus(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}