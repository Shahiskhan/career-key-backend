package com.crear.repositories;

import com.crear.entities.University;
import com.crear.repositories.repohelpers.UniversityStats;
import com.crear.entities.Degree;
import com.crear.entities.Student;
import com.crear.entities.DegreeRequest;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, UUID> {

    // =========================
    // BASIC
    // =========================

    Optional<University> findByName(String name);

    boolean existsByCharterNumber(String charterNumber);

    Optional<University> findByUserId(UUID userId);

    // =========================
    // COUNTS (Dashboard / Profile)
    // =========================

    @Query("select count(s) from Student s where s.university.id = :universityId")
    long countStudentsByUniversityId(UUID universityId);

    @Query("select count(d) from Degree d where d.university.id = :universityId")
    long countDegreesByUniversityId(UUID universityId);

    @Query("select count(dr) from DegreeRequest dr where dr.university.id = :universityId")
    long countDegreeRequestsByUniversityId(UUID universityId);

    @Query("""
            select count(dr)
            from DegreeRequest dr
            where dr.university.id = :universityId
              and dr.status = 'PENDING'
            """)
    long countPendingDegreeRequests(UUID universityId);

    @Query("""
            select count(dr)
            from DegreeRequest dr
            where dr.university.id = :universityId
              and dr.status = 'APPROVED'
            """)
    long countApprovedDegreeRequests(UUID universityId);

    @Query("""
            select count(dr)
            from DegreeRequest dr
            where dr.university.id = :universityId
              and dr.status = 'REJECTED'
            """)
    long countRejectedDegreeRequests(UUID universityId);

    @Query("""
               select
                 (select count(s) from Student s where s.university.id = :id) as totalStudents,
                 (select count(d) from Degree d where d.university.id = :id) as totalDegrees,
                 (select count(dr) from DegreeRequest dr
                     where dr.university.id = :id
                       and dr.status = 'PENDING'
                 ) as pendingRequests
            """)
    UniversityStats fetchStats(UUID id);

}
