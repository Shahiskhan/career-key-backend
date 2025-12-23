package com.crear.controllers;

import com.crear.dtos.ApiResponse;
import com.crear.dtos.degreerequest.DegreeRequestCreateDto;
import com.crear.dtos.degreerequest.DegreeRequestDto;
import com.crear.dtos.degreerequest.DocInfo;
import com.crear.enums.RequestStatus;
import com.crear.services.DegreeRequestService;
import com.crear.utils.DocService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/degree-requests")
@RequiredArgsConstructor
public class DegreeRequestController {

        private final DegreeRequestService degreeRequestService;
        private final DocService docService; // tumhara existing service

        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<ApiResponse<DegreeRequestDto>> createDegreeRequest(
                        @RequestPart("data") DegreeRequestCreateDto dto,
                        @RequestPart(value = "documents", required = false) MultipartFile documents) {

                DocInfo docsInfo = null;
                if (documents != null && !documents.isEmpty()) {
                        docsInfo = docService.saveDocumentWithUserName(documents, dto.getRollNumber());
                }

                DegreeRequestDto created = degreeRequestService.createDegreeRequest(dto, docsInfo);

                ApiResponse<DegreeRequestDto> response = ApiResponse.<DegreeRequestDto>builder()
                                .success(true)
                                .message("Degree request created successfully")
                                .data(created)
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @GetMapping("/university/{universityId}")
        public ResponseEntity<ApiResponse<Page<DegreeRequestDto>>> getByUniversity(
                        @PathVariable UUID universityId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "createdAt") String sortBy,
                        @RequestParam(defaultValue = "desc") String sortDir) {
                Page<DegreeRequestDto> result = degreeRequestService
                                .getDegreeRequestsByUniversity(universityId, page, size, sortBy, sortDir)
                                .orElseThrow(() -> new RuntimeException("No degree requests found"));

                return ResponseEntity.ok(
                                ApiResponse.<Page<DegreeRequestDto>>builder()
                                                .success(true)
                                                .data(result)
                                                .build());
        }

        @GetMapping("/university/{universityId}/status/{status}")
        public ResponseEntity<ApiResponse<Page<DegreeRequestDto>>> getByUniversityAndStatus(
                        @PathVariable UUID universityId,
                        @PathVariable RequestStatus status,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "createdAt") String sortBy,
                        @RequestParam(defaultValue = "desc") String sortDir) {
                Page<DegreeRequestDto> result = degreeRequestService
                                .getDegreeRequestsByUniversityAndStatus(
                                                universityId, status, page, size, sortBy, sortDir)
                                .orElseThrow(() -> new RuntimeException("No degree requests found"));

                return ResponseEntity.ok(
                                ApiResponse.<Page<DegreeRequestDto>>builder()
                                                .success(true)
                                                .data(result)
                                                .build());
        }

        @GetMapping("/student/{studentId}")
        public ResponseEntity<ApiResponse<Page<DegreeRequestDto>>> getByStudent(
                        @PathVariable UUID studentId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "createdAt") String sortBy,
                        @RequestParam(defaultValue = "desc") String sortDir) {
                Page<DegreeRequestDto> result = degreeRequestService
                                .getDegreeRequestsByStudent(studentId, page, size, sortBy, sortDir)
                                .orElseThrow(() -> new RuntimeException("No degree requests found"));

                return ResponseEntity.ok(
                                ApiResponse.<Page<DegreeRequestDto>>builder()
                                                .success(true)
                                                .data(result)
                                                .build());
        }

        @GetMapping("/student/{studentId}/status/{status}")
        public ResponseEntity<ApiResponse<Page<DegreeRequestDto>>> getByStudentAndStatus(
                        @PathVariable UUID studentId,
                        @PathVariable RequestStatus status,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "createdAt") String sortBy,
                        @RequestParam(defaultValue = "desc") String sortDir) {
                Page<DegreeRequestDto> result = degreeRequestService
                                .getDegreeRequestsByStudentAndStatus(
                                                studentId, status, page, size, sortBy, sortDir)
                                .orElseThrow(() -> new RuntimeException("No degree requests found"));

                return ResponseEntity.ok(
                                ApiResponse.<Page<DegreeRequestDto>>builder()
                                                .success(true)
                                                .data(result)
                                                .build());
        }

        @GetMapping("/status/{status}")
        public ResponseEntity<ApiResponse<Page<DegreeRequestDto>>> getByStatus(
                        @PathVariable RequestStatus status,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "createdAt") String sortBy,
                        @RequestParam(defaultValue = "desc") String sortDir) {
                Page<DegreeRequestDto> result = degreeRequestService
                                .getDegreeRequestsByStatus(status, page, size, sortBy, sortDir)
                                .orElseThrow(() -> new RuntimeException("No degree requests found"));

                return ResponseEntity.ok(
                                ApiResponse.<Page<DegreeRequestDto>>builder()
                                                .success(true)
                                                .data(result)
                                                .build());
        }

}
