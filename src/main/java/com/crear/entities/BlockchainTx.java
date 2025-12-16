package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "blockchain_txs", indexes = @Index(columnList = "txHash", name = "idx_tx_hash"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockchainTx extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "degree_id", nullable = false)
    private Degree degree;

    private String txHash;
    private Long blockNumber;
    private String contractAddress;
    private Long gasUsed;

    private String senderAddress;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private com.crear.enums.AnchoringStatus anchoringStatus = com.crear.enums.AnchoringStatus.PENDING;

    private Instant confirmationTime;
}
