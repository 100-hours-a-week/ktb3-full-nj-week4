package com.example.dance_community.dto.registration;

import lombok.AllArgsConstructor;
import java.util.List;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationsResponse {
    private String message;
    private List<RegistrationDto> data;
}