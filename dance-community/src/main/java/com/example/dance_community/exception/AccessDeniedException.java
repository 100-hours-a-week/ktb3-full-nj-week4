package com.example.dance_community.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String msg){ super(msg); }
}
