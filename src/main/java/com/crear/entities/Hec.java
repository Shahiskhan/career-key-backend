package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

import com.crear.auth.model.User;

@Entity
@Table(name = "hec")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hec extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private User user;

    @Column(unique = true)
    private String hecCode; // e.g., HEC-PAK

    private String name;
    private String headOfficeAddress;

    // path to digital seal cert (private key / cert storage should be secure)
    private String digitalSealCertPath;

    @OneToMany(mappedBy = "hec")
    private Set<University> universities;

    @OneToMany(mappedBy = "hecOfficer")
    private Set<Degree> stampedDegrees;
}
