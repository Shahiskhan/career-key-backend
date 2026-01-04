package com.crear.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crear.auth.dto.UniversityDto;
import com.crear.auth.model.User;
import com.crear.auth.repository.UserRepository;
import com.crear.entities.University;
import com.crear.repositories.UniversityRepository;
import com.crear.services.UniversityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Slf4j
public class UniversityController {

  private final UniversityService universityService;
  private UniversityRepository universityRepository;

  @GetMapping("hec/universities")
  public ResponseEntity<List<UniversityDto>> getAllUniversities() {
    return ResponseEntity.ok(universityService.getAllUniversity());
  }

  /**
   * Fetch university dashboard / profile by universityId
   * 
   * @param id UUID of university (frontend provides)
   */
  @GetMapping("/{id}/dashboard")
  public ResponseEntity<UniversityDto> getUniversityDashboard(@PathVariable UUID id) {

    UniversityDto dto = universityService.getUniversityByUserId(id);
    return ResponseEntity.ok(dto);
  }
}
