package com.crear.repositories;

import com.crear.auth.model.User;
import com.crear.entities.Hec;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HecRepository extends JpaRepository<Hec, UUID> {
  // existsByHecCode
  boolean existsByHecCode(String hecCode);

  // get hec by user
  Optional<Hec> findByUserId(UUID userId);

  Optional<Hec> findByUser(User user);
}
