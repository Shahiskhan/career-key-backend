package com.crear.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.PreUpdate;
import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private Instant createdOn = Instant.now();

    @Column(nullable = false)
    private Instant updatedOn = Instant.now();

    private Long createdByUserId;
    private Long updatedByUserId;

    // Soft-delete
    private Boolean deleted = false;
    private Long deletedBy;
    private Instant deletedOn;

    @PreUpdate
    protected void onUpdate() {
        this.updatedOn = Instant.now();
    }
}
