package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "qr_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QRCode extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "degree_id", nullable = false, unique = true)
    private Degree degree;

    @Column(columnDefinition = "text")
    private String qrPayloadJson; // JSON containing txHash & cid etc.

    @Builder.Default
    private Instant generatedOn = Instant.now();
    private Instant expiresOn;
}
