package com.crear.repositories;

import com.crear.auth.model.User;
import com.crear.entities.Student;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsByCnic(String cnic);

    Optional<Student> findByRollNumber(String rollNumber);

    Optional<Student> findByUser(User user);

}
