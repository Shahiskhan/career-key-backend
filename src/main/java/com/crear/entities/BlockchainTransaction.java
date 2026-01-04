package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.Instant;

@Entity
@Table(name = "blockchain_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockchainTransaction extends BaseEntity {

  @OneToOne
  @JoinColumn(name = "degree_request_id", nullable = false, unique = true)
  private DegreeRequest degreeRequest;

  @Column(nullable = false, unique = true)
  private String transactionHash;

  private BigInteger blockNumber;

  private BigInteger gasUsed;

  private String contractAddress;

  @Column(length = 20)
  private String status; // PENDING / CONFIRMED / FAILED

  @Builder.Default
  private Instant createdAt = Instant.now();
}
