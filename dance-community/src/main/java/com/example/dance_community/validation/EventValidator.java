package com.example.dance_community.validation;

import com.example.dance_community.dto.event.EventCreateRequest;
import com.example.dance_community.enums.EventType;
import com.example.dance_community.enums.Scope;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventValidator implements ConstraintValidator<ValidScopeTypeEvent, EventCreateRequest> {

    @Override
    public boolean isValid(EventCreateRequest eventCreateRequest, ConstraintValidatorContext context) {
        try {
            Scope.valueOf(eventCreateRequest.scope().toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }

        try {
            EventType.valueOf(eventCreateRequest.type().toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }

        if ("CLUB".equalsIgnoreCase(eventCreateRequest.scope()) && eventCreateRequest.clubId() == null) {
            return false;
        }

        return true;
    }
}