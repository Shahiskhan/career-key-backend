package com.crear.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crear.dtos.ApiResponse;
import com.crear.dtos.DegreeRequestVerifierDto;
import com.crear.services.basic.VerifierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/verifier")
public class VerifierController {

  private final VerifierService verifierService;

  @PostMapping("/degree-requests/{degreeRequestId}/verify")
  public ResponseEntity<ApiResponse<DegreeRequestVerifierDto>> verifyDegreeRequest(
      @PathVariable String degreeRequestId) { // 1. Changed from UUID to String

    try {
      // 2. Manual conversion to UUID
      UUID uuid;
      try {
        uuid = UUID.fromString(degreeRequestId);
      } catch (IllegalArgumentException e) {
        // 3. Return a custom error if the string is not a valid UUID
        return ResponseEntity.badRequest().body(
            ApiResponse.<DegreeRequestVerifierDto>builder()
                .success(false)
                .message("Invalid ID format: " + degreeRequestId + " is not a valid UUID.")
                .build());
      }

      // 4. Proceed with logic using the converted 'uuid'
      DegreeRequestVerifierDto dto = verifierService.verifyDegreeRequestById(uuid);

      return ResponseEntity.ok(
          ApiResponse.<DegreeRequestVerifierDto>builder()
              .success(true)
              .data(dto)
              .message("Degree request verified successfully")
              .build());

    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(
          ApiResponse.<DegreeRequestVerifierDto>builder()
              .success(false)
              .message("Failed to verify degree request: " + e.getMessage())
              .build());
    }
  }

}
