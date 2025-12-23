package com.crear.repositories;

import com.crear.entities.Hec;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HecRepository extends JpaRepository<Hec, UUID> {
  // existsByHecCode
  boolean existsByHecCode(String hecCode);
}
