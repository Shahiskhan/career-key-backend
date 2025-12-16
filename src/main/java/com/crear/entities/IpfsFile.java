package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "ipfs_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IpfsFile extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "degree_id", nullable = false, unique = true)
    private Degree degree;

    private String cid;
    private String fileName;
    private String mimeType;
    @Builder.Default
    private Instant uploadedOn = Instant.now();
    private Long uploadedByUserId;
}
