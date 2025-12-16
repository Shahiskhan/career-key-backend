package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

import com.crear.enums.RequestStatus;

@Entity
@Table(name = "degree_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DegreeRequest extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    private String program;
    private String rollNumber;
    private Integer passingYear;
    private Double cgpa;

    private String documentPath;

    // private boolean verifiedByUniversity=false;
    // private boolean stampedByHec=false;

    
    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false)
    private RequestStatus verificationStatus = RequestStatus.PENDING;



    @Builder.Default
    private Instant requestDate = Instant.now();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;

    @Column(columnDefinition = "text")
    private String remarks;

    @OneToOne(mappedBy = "degreeRequest", cascade = CascadeType.ALL)
    private Degree degree;

    @OneToOne(mappedBy = "degreeRequest", cascade = CascadeType.ALL)
    private Payment payment;
}
