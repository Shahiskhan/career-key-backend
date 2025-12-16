package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

import com.crear.auth.model.User;

@Entity
@Table(name = "universities", indexes = @Index(columnList = "charterNumber", name = "idx_uni_charter"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class University extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private User user;

    @Column(nullable = false)
    private String name;

    private String city;

    @Column(unique = true)
    private String charterNumber;

    private String issuingAuthority;
    @Builder.Default
    private Boolean hecRecognized = false;

    @ManyToOne
    @JoinColumn(name = "hec_id")
    private Hec hec;

    @OneToMany(mappedBy = "university")
    private Set<Student> students;

    @OneToMany(mappedBy = "university")
    private Set<DegreeRequest> degreeRequests;

    @OneToMany(mappedBy = "university")
    private Set<Degree> degrees;
}
