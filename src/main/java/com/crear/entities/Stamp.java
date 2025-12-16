package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "stamps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stamp extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "degree_id", nullable = false, unique = true)
    private Degree degree;

    private String stampImagePath;
    private Long stampingOfficerUserId;
    @Builder.Default
    private Instant stampDate = Instant.now();

    @Column(columnDefinition = "text")
    private String comments;
}
