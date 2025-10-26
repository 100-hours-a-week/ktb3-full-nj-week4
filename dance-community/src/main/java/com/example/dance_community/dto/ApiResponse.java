package com.example.dance_community.dto;

public record ApiResponse<T>(String message, T data) {
}