package com.example.dance_community.validation;

import com.example.dance_community.dto.post.PostRequest;
import com.example.dance_community.entity.enums.Scope;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PostValidator implements ConstraintValidator<ValidScopePost, PostRequest> {

    @Override
    public boolean isValid(PostRequest postRequest, ConstraintValidatorContext context) {
        try {
            Scope.valueOf(postRequest.getScope().toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }

        if ("CLUB".equalsIgnoreCase(postRequest.getScope()) && postRequest.getClubId() == null) {
            return false;
        }

        return true;
    }
}
