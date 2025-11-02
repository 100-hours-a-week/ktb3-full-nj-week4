package com.example.dance_community.validation;

import com.example.dance_community.dto.post.PostUpdateRequest;
import com.example.dance_community.entity.enums.Scope;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PostValidator implements ConstraintValidator<ValidScopePost, PostUpdateRequest> {

    @Override
    public boolean isValid(PostUpdateRequest postUpdateRequest, ConstraintValidatorContext context) {
        try {
            Scope.valueOf(postUpdateRequest.getScope().toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }

        if ("CLUB".equalsIgnoreCase(postUpdateRequest.getScope()) && postUpdateRequest.getClubId() == null) {
            return false;
        }

        return true;
    }
}
