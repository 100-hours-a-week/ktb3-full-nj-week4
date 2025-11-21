package com.example.dance_community.validation;

import com.example.dance_community.dto.post.PostCreateRequest;
import com.example.dance_community.enums.Scope;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PostValidator implements ConstraintValidator<ValidScopePost, PostCreateRequest> {

    @Override
    public boolean isValid(PostCreateRequest postCreateRequest, ConstraintValidatorContext context) {
        try {
            Scope.valueOf(postCreateRequest.getScope().toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }

        if ("CLUB".equalsIgnoreCase(postCreateRequest.getScope()) && postCreateRequest.getClubId() == null) {
            return false;
        }

        return true;
    }
}
