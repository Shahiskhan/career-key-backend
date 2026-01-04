package com.crear.services.impl;

import com.crear.services.DegreeRequestService;
import com.crear.dtos.degreerequest.DegreeRequestCreateDto;
import com.crear.dtos.degreerequest.DegreeRequestDto;
import com.crear.dtos.degreerequest.DocInfo;
import com.crear.entities.DegreeRequest;
import com.crear.entities.Student;
import com.crear.entities.University;
import com.crear.enums.DocumentStatus;
import com.crear.enums.RequestStatus;
import com.crear.exceptions.ResourceNotFoundException;
import com.crear.repositories.DegreeRequestRepository;
import com.crear.repositories.StudentRepository;
import com.crear.repositories.UniversityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DegreeRequestServiceImpl implements DegreeRequestService {

        private final DegreeRequestRepository degreeRequestRepository;
        private final StudentRepository studentRepository;
        private final UniversityRepository universityRepository;

        @Override
        public void updateDegreeRequest(DegreeRequest degreeRequest) {
                degreeRequestRepository.save(degreeRequest);

        }

        // CREATE
        @Override
        @Transactional
        public DegreeRequestDto createDegreeRequest(
                        DegreeRequestCreateDto dto,
                        DocInfo documents) {

                Student student = studentRepository.findByRollNumber(dto.getRollNumber())
                                .orElseThrow(() -> new RuntimeException(
                                                "Student not found with roll number: " + dto.getRollNumber()));
                University university = universityRepository.findById(dto.getUniversityId())
                                .orElseThrow(() -> new RuntimeException(
                                                "University not found with id: " + dto.getUniversityId()));
                DegreeRequest request = DegreeRequest.builder()
                                .student(student)
                                .university(university)
                                .program(dto.getProgram())
                                .rollNumber(dto.getRollNumber())
                                .passingYear(dto.getPassingYear())
                                .cgpa(dto.getCgpa())
                                .remarks(dto.getRemarks())
                                .documentPath(documents.getFilePath())
                                .verificationStatus(RequestStatus.PENDING)
                                .documentStatus(DocumentStatus.PENDING)
                                .build();

                DegreeRequest savedRequest = degreeRequestRepository.save(request);

                // 🔥 Entity → DTO response
                return mapToDto(savedRequest);
        }

        @Override
        public DegreeRequest getDegreeRequestById(UUID degreeRequestId) {

                DegreeRequest request = degreeRequestRepository.findById(degreeRequestId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "didnot found degreeRequest for this id" + degreeRequestId));
                return request;
        }

        // UNIVERSITY

        @Override
        public Optional<Page<DegreeRequestDto>> getDegreeRequestsByUniversity(
                        UUID userId, int page, int size, String sortBy, String sortDir) {

                Pageable pageable = buildPageable(page, size, sortBy, sortDir);
                University university = universityRepository.findByUserId(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "University not found for this id" + userId));
                UUID universityId = university.getId();

                Page<DegreeRequest> entityPage = degreeRequestRepository.findByUniversityId(universityId, pageable);

                return mapToDtoPage(entityPage);
        }

        @Override
        public Optional<Page<DegreeRequestDto>> getDegreeRequestsByUniversityAndStatus(
                        UUID userId, RequestStatus status,
                        int page, int size, String sortBy, String sortDir) {

                Pageable pageable = buildPageable(page, size, sortBy, sortDir);

                University university = universityRepository.findByUserId(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "University not found for this id" + userId));
                UUID universityId = university.getId();

                Page<DegreeRequest> entityPage = degreeRequestRepository.findByUniversityIdAndVerificationStatus(
                                universityId, status, pageable);

                return mapToDtoPage(entityPage);
        }

        // STUDENT

        @Override
        public Optional<Page<DegreeRequestDto>> getDegreeRequestsByStudent(
                        UUID studentId, int page, int size, String sortBy, String sortDir) {

                Pageable pageable = buildPageable(page, size, sortBy, sortDir);

                Page<DegreeRequest> entityPage = degreeRequestRepository.findByStudentId(studentId, pageable);

                return mapToDtoPage(entityPage);
        }

        @Override
        public Optional<Page<DegreeRequestDto>> getDegreeRequestsByStudentAndStatus(
                        UUID studentId, RequestStatus status,
                        int page, int size, String sortBy, String sortDir) {

                Pageable pageable = buildPageable(page, size, sortBy, sortDir);

                Page<DegreeRequest> entityPage = degreeRequestRepository.findByStudentIdAndVerificationStatus(
                                studentId, status, pageable);

                return mapToDtoPage(entityPage);
        }

        // ADMIN HEC

        @Override
        public Optional<Page<DegreeRequestDto>> getDegreeRequestsByStatus(
                        RequestStatus status, int page, int size, String sortBy, String sortDir) {

                Pageable pageable = buildPageable(page, size, sortBy, sortDir);

                Page<DegreeRequest> entityPage = degreeRequestRepository.findByVerificationStatus(status, pageable);

                return mapToDtoPage(entityPage);
        }

        // HELPERS

        private Pageable buildPageable(int page, int size, String sortBy, String sortDir) {

                if (size <= 0)
                        size = 10;
                if (page < 0)
                        page = 0;
                if (sortBy == null || sortBy.isBlank())
                        sortBy = "createdAt";

                Sort sort = "desc".equalsIgnoreCase(sortDir)
                                ? Sort.by(sortBy).descending()
                                : Sort.by(sortBy).ascending();

                return PageRequest.of(page, size, sort);
        }

        private Optional<Page<DegreeRequestDto>> mapToDtoPage(Page<DegreeRequest> entityPage) {

                if (entityPage.isEmpty()) {
                        return Optional.empty();
                }

                Page<DegreeRequestDto> dtoPage = entityPage.map(this::mapToDto);

                return Optional.of(dtoPage);
        }

        private DegreeRequestDto mapToDto(DegreeRequest request) {
                log.debug("Mapping DegreeRequest entity to DTO for request id: {}", request.getDocumentStatus());

                return DegreeRequestDto.builder()
                                .id(request.getId())
                                .studentName(request.getStudent().getFullName())
                                .universityName(request.getUniversity().getName())
                                .program(request.getProgram())
                                .rollNumber(request.getRollNumber())
                                .passingYear(request.getPassingYear())
                                // .stampedByHec(request.isStampedByHec())
                                .cgpa(request.getCgpa())
                                .documentPath(request.getDocumentPath())
                                .requestDate(request.getCreatedOn())
                                .status(request.getVerificationStatus())
                                .remarks(request.getRemarks())
                                .documentStatus(request.getDocumentStatus())
                                .ipfsHash(request.getIpfsHash())
                                .ipfsProvider(request.getIpfsProvider())
                                .build();
        }

        @Override
        @Transactional
        public DegreeRequestDto verifyByUniversity(UUID degreeRequestId, UUID universityId) {
                log.info("Verifying degreeRequest {} for university {}", degreeRequestId, universityId);

                University university = universityRepository.findByUserId(universityId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "University not found for this id" + universityId));

                DegreeRequest request = degreeRequestRepository.findById(degreeRequestId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "DegreeRequest not found: " + degreeRequestId));

                if (!request.getUniversity().getId().equals(university.getId())) {
                        throw new RuntimeException("University does not match for this degree request!");
                }

                request.setVerificationStatus(RequestStatus.VERIFIED_BY_UNIVERSITY);
                degreeRequestRepository.save(request);

                log.info("DegreeRequest {} verified by university {}", degreeRequestId, universityId);
                return mapToDto(request);
        }

        @Override
        @Transactional
        public DegreeRequestDto verifyByHec(UUID degreeRequestId) {
                log.info("Verifying degreeRequest {} by HEC", degreeRequestId);

                DegreeRequest request = degreeRequestRepository.findById(degreeRequestId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "DegreeRequest not found: " + degreeRequestId));

                request.setVerificationStatus(RequestStatus.VERIFIED_BY_HEC);
                degreeRequestRepository.save(request);

                log.info("DegreeRequest {} verified by HEC", degreeRequestId);
                return mapToDto(request);
        }

        @Override
        public DegreeRequestDto updateDegreeRequestDto(DegreeRequest degreeRequest) {

                degreeRequestRepository.save(degreeRequest);
                DegreeRequestDto degreeRequestDto = mapToDto(degreeRequest);

                return degreeRequestDto;
        }

}
