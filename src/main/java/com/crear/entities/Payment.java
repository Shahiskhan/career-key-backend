package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "degree_request_id", nullable = false, unique = true)
    private DegreeRequest degreeRequest;

    private Double amount;
    private String currency;
    private String transactionRef; // gateway ref

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private com.crear.enums.PaymentStatus paymentStatus = com.crear.enums.PaymentStatus.INIT;

    private Instant paidOn;
    private String paymentGateway;
}
