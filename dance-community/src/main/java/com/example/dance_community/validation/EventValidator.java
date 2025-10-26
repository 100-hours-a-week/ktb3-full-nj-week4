package com.example.dance_community.validation;

import com.example.dance_community.dto.event.EventUpdateRequest;
import com.example.dance_community.enums.EventType;
import com.example.dance_community.enums.Scope;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventValidator implements ConstraintValidator<ValidScopeTypeEvent, EventUpdateRequest> {

    @Override
    public boolean isValid(EventUpdateRequest eventUpdateRequest, ConstraintValidatorContext context) {
        try {
            Scope.valueOf(eventUpdateRequest.getScope().toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }

        try {
            EventType.valueOf(eventUpdateRequest.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }

        if ("CLUB".equalsIgnoreCase(eventUpdateRequest.getScope()) && eventUpdateRequest.getClubId() == null) {
            return false;
        }

        return true;
    }
}