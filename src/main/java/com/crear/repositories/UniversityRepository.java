package com.crear.repositories;

import com.crear.entities.University;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, UUID> {
    // find by university name
    Optional<University> findByName(String name);

    // find by user id
    Optional<University> findByUserId(UUID userId);

}
