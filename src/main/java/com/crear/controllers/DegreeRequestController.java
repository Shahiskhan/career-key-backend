package com.crear.controllers;

import com.crear.dtos.ApiResponse;
import com.crear.dtos.degreerequest.DegreeRequestCreateDto;
import com.crear.dtos.degreerequest.DegreeRequestDto;
import com.crear.dtos.degreerequest.DocInfo;
import com.crear.enums.RequestStatus;
import com.crear.services.DegreeRequestService;
import com.crear.services.basic.DocumentService;
import com.crear.utils.DocService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/degree-requests")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class DegreeRequestController {

        private final DegreeRequestService degreeRequestService;
        private final DocService docService;
        private final DocumentService documentService;

        // =====================================================
        // 🔹 HELPER METHOD (DOCUMENT ATTACHER)
        // =====================================================
        private void attachDocuments(Page<DegreeRequestDto> page) {
                page.forEach(dto -> {
                        if (dto.getDocumentPath() != null && !dto.getDocumentPath().isBlank()) {
                                try {
                                        byte[] bytes = documentService.getDocumentByPath(dto.getDocumentPath());
                                        dto.setDocumentBase64(Base64.getEncoder().encodeToString(bytes));
                                } catch (Exception e) {
                                        log.error("Failed to load document for DTO id {}", dto.getId(), e);
                                }
                        }
                });
        }

        // =====================================================
        // 🔹 CREATE DEGREE REQUEST
        // =====================================================
        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<ApiResponse<DegreeRequestDto>> createDegreeRequest(
                        @RequestPart("data") DegreeRequestCreateDto dto,
                        @RequestPart(value = "documents", required = false) MultipartFile documents) {

                DocInfo docsInfo = null;
                if (documents != null && !documents.isEmpty()) {
                        docsInfo = docService.saveDocumentWithUserName(documents, dto.getRollNumber());
                }

                DegreeRequestDto created = degreeRequestService.createDegreeRequest(dto, docsInfo);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ApiResponse.<DegreeRequestDto>builder()
                                                .success(true)
                                                .message("Degree request created successfully")
                                                .data(created)
                                                .build());
        }

        // =====================================================
        // 🔹 UNIVERSITY
        // =====================================================
        @GetMapping("/university/{universityId}")
        public ResponseEntity<ApiResponse<Page<DegreeRequestDto>>> getByUniversity(
                        @PathVariable UUID universityId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "createdOn") String sortBy,
                        @RequestParam(defaultValue = "desc") String sortDir) {

                Page<DegreeRequestDto> result = degreeRequestService
                                .getDegreeRequestsByUniversity(universityId, page, size, sortBy, sortDir)
                                .orElseThrow(() -> new RuntimeException("No degree requests found"));

                attachDocuments(result);

                return ResponseEntity.ok(ApiResponse.<Page<DegreeRequestDto>>builder()
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
                        @RequestParam(defaultValue = "createdOn") String sortBy,
                        @RequestParam(defaultValue = "desc") String sortDir) {

                Page<DegreeRequestDto> result = degreeRequestService
                                .getDegreeRequestsByUniversityAndStatus(universityId, status, page, size, sortBy,
                                                sortDir)
                                .orElseThrow(() -> new RuntimeException("No degree requests found"));

                attachDocuments(result);

                return ResponseEntity.ok(ApiResponse.<Page<DegreeRequestDto>>builder()
                                .success(true)
                                .data(result)
                                .build());
        }

        // =====================================================
        // 🔹 STUDENT
        // =====================================================
        @GetMapping("/student/{studentId}")
        public ResponseEntity<ApiResponse<Page<DegreeRequestDto>>> getByStudent(
                        @PathVariable UUID studentId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "createdOn") String sortBy,
                        @RequestParam(defaultValue = "desc") String sortDir) {

                Page<DegreeRequestDto> result = degreeRequestService
                                .getDegreeRequestsByStudent(studentId, page, size, sortBy, sortDir)
                                .orElseThrow(() -> new RuntimeException("No degree requests found"));

                attachDocuments(result);

                return ResponseEntity.ok(ApiResponse.<Page<DegreeRequestDto>>builder()
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
                        @RequestParam(defaultValue = "createdOn") String sortBy,
                        @RequestParam(defaultValue = "desc") String sortDir) {

                Page<DegreeRequestDto> result = degreeRequestService
                                .getDegreeRequestsByStudentAndStatus(studentId, status, page, size, sortBy, sortDir)
                                .orElseThrow(() -> new RuntimeException("No degree requests found"));

                attachDocuments(result);

                return ResponseEntity.ok(ApiResponse.<Page<DegreeRequestDto>>builder()
                                .success(true)
                                .data(result)
                                .build());
        }

        // =====================================================
        // 🔹 STATUS ONLY
        // =====================================================
        @GetMapping("/status/{status}")
        public ResponseEntity<ApiResponse<Page<DegreeRequestDto>>> getByStatus(
                        @PathVariable RequestStatus status,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "createdOn") String sortBy,
                        @RequestParam(defaultValue = "desc") String sortDir) {

                Page<DegreeRequestDto> result = degreeRequestService
                                .getDegreeRequestsByStatus(status, page, size, sortBy, sortDir)
                                .orElseThrow(() -> new RuntimeException("No degree requests found"));

                attachDocuments(result);

                return ResponseEntity.ok(ApiResponse.<Page<DegreeRequestDto>>builder()
                                .success(true)
                                .data(result)
                                .build());
        }

        @PostMapping("/{degreeRequestId}/verify/university/{universityId}")
        public ResponseEntity<ApiResponse<DegreeRequestDto>> verifyByUniversity(
                        @PathVariable UUID degreeRequestId,
                        @PathVariable UUID universityId) {

                log.info("Controller: verify degreeRequest {} by university {}",
                                degreeRequestId, universityId);

                DegreeRequestDto dto = degreeRequestService.verifyByUniversity(degreeRequestId, universityId);

                // document attach (optional but consistent)
                if (dto.getDocumentPath() != null && !dto.getDocumentPath().isBlank()) {
                        try {
                                byte[] bytes = documentService.getDocumentByPath(dto.getDocumentPath());
                                dto.setDocumentBase64(Base64.getEncoder().encodeToString(bytes));
                        } catch (Exception e) {
                                log.error("Failed to attach document after university verification", e);
                        }
                }

                return ResponseEntity.ok(
                                ApiResponse.<DegreeRequestDto>builder()
                                                .success(true)
                                                .message("Degree request verified by university")
                                                .data(dto)
                                                .build());
        }

        @PostMapping("/{degreeRequestId}/verify/hec")
        public ResponseEntity<ApiResponse<DegreeRequestDto>> verifyByHec(
                        @PathVariable UUID degreeRequestId) {

                log.info("Controller: verify degreeRequest {} by HEC", degreeRequestId);

                DegreeRequestDto dto = degreeRequestService.verifyByHec(degreeRequestId);

                if (dto.getDocumentPath() != null && !dto.getDocumentPath().isBlank()) {
                        try {
                                byte[] bytes = documentService.getDocumentByPath(dto.getDocumentPath());
                                dto.setDocumentBase64(Base64.getEncoder().encodeToString(bytes));
                        } catch (Exception e) {
                                log.error("Failed to attach document after HEC verification", e);
                        }
                }

                return ResponseEntity.ok(
                                ApiResponse.<DegreeRequestDto>builder()
                                                .success(true)
                                                .message("Degree request verified by HEC")
                                                .data(dto)
                                                .build());
        }

}
