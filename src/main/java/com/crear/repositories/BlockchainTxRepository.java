package com.crear.repositories;

import com.crear.entities.BlockchainTx;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockchainTxRepository extends JpaRepository<BlockchainTx, UUID> {
}
