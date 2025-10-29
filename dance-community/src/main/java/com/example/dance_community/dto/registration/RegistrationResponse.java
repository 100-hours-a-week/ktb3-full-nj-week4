package com.example.dance_community.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationResponse {
    private String message;
    private RegistrationDto data;
}
