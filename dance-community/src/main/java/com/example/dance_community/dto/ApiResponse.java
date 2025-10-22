package com.example.dance_community.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // 응답 값 중 null인 값 제외
public record ApiResponse<T>(String message, T data) {
}