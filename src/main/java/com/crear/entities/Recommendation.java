package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "recommendations", indexes = @Index(columnList = "student_id,job_post_id", name = "idx_rec_student_job"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "job_post_id", nullable = false)
    private JobPost jobPost;

    private Double matchScore;

    @Column(columnDefinition = "text")
    private String explanationText;

    @Builder.Default
    private Boolean isBookmarked = false;
    @Builder.Default
    private Boolean isApplied = false;

    @Builder.Default
    private Instant createdOn = Instant.now();
}
