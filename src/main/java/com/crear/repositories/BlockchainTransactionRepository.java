package com.crear.repositories;

import com.crear.entities.BlockchainTransaction;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockchainTransactionRepository
    extends JpaRepository<BlockchainTransaction, UUID> {
}
