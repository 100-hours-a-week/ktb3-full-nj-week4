package com.example.dance_community.validation;

import com.example.dance_community.dto.event.EventRequest;
import com.example.dance_community.enums.EventType;
import com.example.dance_community.enums.Scope;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventValidator implements ConstraintValidator<ValidScopeTypeEvent, EventRequest> {

    @Override
    public boolean isValid(EventRequest eventRequest, ConstraintValidatorContext context) {
        try {
            Scope.valueOf(eventRequest.getScope().toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }

        try {
            EventType.valueOf(eventRequest.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }

        if ("CLUB".equalsIgnoreCase(eventRequest.getScope()) && eventRequest.getClubId() == null) {
            return false;
        }

        return true;
    }
}