package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "degrees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Degree extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "request_id", nullable = false, unique = true)
    private DegreeRequest degreeRequest;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private String program;
    private Double cgpa;
    private Integer passingYear;
    private Instant issueDate;

    // verification & tamper-proofing
    private String verificationHash; // e.g., SHA256 of PDF
    private String ipfsCid;
    private String qrText;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private com.crear.enums.DegreeStatus status = com.crear.enums.DegreeStatus.UNIVERSITY_VERIFIED;

    @OneToOne(mappedBy = "degree", cascade = CascadeType.ALL)
    private Stamp stamp;

    @OneToOne(mappedBy = "degree", cascade = CascadeType.ALL)
    private IpfsFile ipfsFile;

    @OneToMany(mappedBy = "degree", cascade = CascadeType.ALL)
    private Set<BlockchainTx> blockchainTxs;

    @OneToOne(mappedBy = "degree", cascade = CascadeType.ALL)
    private QRCode qrCode;

    @ManyToOne
    @JoinColumn(name = "hec_officer_id")
    private Hec hecOfficer;
}
