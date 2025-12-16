package com.crear.services;

import com.crear.dtos.degreerequest.DegreeRequestCreateDto;
import com.crear.dtos.degreerequest.DegreeRequestDto;
import com.crear.dtos.degreerequest.DocInfo;
import com.crear.entities.DegreeRequest;
import com.crear.enums.RequestStatus;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface DegreeRequestService {

        DegreeRequestDto createDegreeRequest(
                        DegreeRequestCreateDto dto,
                        DocInfo documents);

        // // 1️⃣ Create degree request
        // DegreeRequest createDegreeRequest(
        // DegreeRequestCreateDto dto,
        // DocInfo documents);

        Optional<Page<DegreeRequestDto>> getDegreeRequestsByUniversity(
                        UUID universityId,
                        int page,
                        int size,
                        String sortBy,
                        String sortDir);

        Optional<Page<DegreeRequestDto>> getDegreeRequestsByUniversityAndStatus(
                        UUID universityId,
                        RequestStatus status,
                        int page,
                        int size,
                        String sortBy,
                        String sortDir);

        Optional<Page<DegreeRequestDto>> getDegreeRequestsByStudent(
                        UUID studentId,
                        int page,
                        int size,
                        String sortBy,
                        String sortDir);

        Optional<Page<DegreeRequestDto>> getDegreeRequestsByStudentAndStatus(
                        UUID studentId,
                        RequestStatus status,
                        int page,
                        int size,
                        String sortBy,
                        String sortDir);

        Optional<Page<DegreeRequestDto>> getDegreeRequestsByStatus(
                        RequestStatus status,
                        int page,
                        int size,
                        String sortBy,
                        String sortDir);
}
