package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "audit_logs", indexes = @Index(columnList = "actorUserId", name = "idx_audit_actor"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog extends BaseEntity {

    private Long actorUserId;
    private String entityType; // e.g., DegreeRequest, Degree
    private Long entityId;

    @Column(columnDefinition = "text")
    private String oldValue;

    @Column(columnDefinition = "text")
    private String newValue;

    private String action; // e.g., SUBMIT, APPROVE, STAMP, ANCHOR

    @Builder.Default
    private Instant actionTime = Instant.now();
}
