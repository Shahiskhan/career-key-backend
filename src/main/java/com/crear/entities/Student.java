package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

import com.crear.auth.model.User;

@Entity
@Table(name = "students", indexes = @Index(columnList = "cnic", name = "idx_student_cnic"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private User user;

    @Column(nullable = false, unique = true)
    private String cnic;
    private String rollNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String contactNumber;
    private String address;

    // Soft-delete / graduation
    @Builder.Default
    private Boolean graduated = false;

    @Column(columnDefinition = "text")
    private String skillsJson; // optional: small JSON array or keep normalized via skill tables

    @Column(columnDefinition = "text")
    private String certificationsJson;

    @Column(columnDefinition = "text")
    private String internshipsJson;

    // resumeDocumnet oath
    private String resumeDocumentPath;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<DegreeRequest> degreeRequests;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Degree> degrees;
}
