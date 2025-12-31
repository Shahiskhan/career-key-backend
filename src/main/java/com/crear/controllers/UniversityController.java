package com.crear.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crear.auth.dto.UniversityDto;
import com.crear.services.UniversityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UniversityController {

  private final UniversityService universityService;

  @GetMapping("/universities")
  public ResponseEntity<List<UniversityDto>> getAllUniversities() {
    return ResponseEntity.ok(universityService.getAllUniversity());
  }
}
