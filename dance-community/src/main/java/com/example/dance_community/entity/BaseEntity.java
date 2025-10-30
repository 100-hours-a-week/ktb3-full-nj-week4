package com.example.dance_community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Where(clause = "is_deleted = false")
public abstract class BaseEntity {

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    // DELETE
    public void delete() {
        this.isDeleted = true;
    }
}