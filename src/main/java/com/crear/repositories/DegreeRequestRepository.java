package com.crear.repositories;

import com.crear.entities.DegreeRequest;
import com.crear.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DegreeRequestRepository extends JpaRepository<DegreeRequest, UUID> {

    // 1️⃣ Get all requests by University (Paginated)
    Page<DegreeRequest> findByUniversityId(UUID universityId, Pageable pageable);

    // 2️⃣ Get all requests by University + RequestStatus (Paginated)
    Page<DegreeRequest> findByUniversityIdAndVerificationStatus(
            UUID universityId,
            RequestStatus verificationStatus,
            Pageable pageable
    );

    // 3️⃣ Get all requests by Student (Paginated)
    Page<DegreeRequest> findByStudentId(UUID studentId, Pageable pageable);

    // 4️⃣ Get all requests by Student + RequestStatus (Paginated)
    Page<DegreeRequest> findByStudentIdAndVerificationStatus(
            UUID studentId,
            RequestStatus verificationStatus,
            Pageable pageable
    );

    // 5️⃣ Get all requests by RequestStatus only (Admin / HEC use case)
    Page<DegreeRequest> findByVerificationStatus(
            RequestStatus verificationStatus,
            Pageable pageable
    );

}
