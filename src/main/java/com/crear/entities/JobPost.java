package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "job_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPost extends BaseEntity {

    private String title;
    private String company;
    private String location;

    @Column(columnDefinition = "text")
    private String descriptionSnippet;

    private String postUrl;
    private Instant postDate;
    private Instant expiryDate;

    private String sourceType; // LINKEDIN, ROZEE, INDEED

    @Builder.Default
    private Boolean expired = false;
}
