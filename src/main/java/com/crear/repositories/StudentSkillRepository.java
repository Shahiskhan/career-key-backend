package com.crear.repositories;

import com.crear.entities.Student;
import com.crear.entities.StudentSkill;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentSkillRepository extends JpaRepository<StudentSkill, UUID> {

    // delete all student skills for a given student
    void deleteAllByStudent(Student student);

    // find all skills for a student
    Set<StudentSkill> findByStudent(Student student);
}
