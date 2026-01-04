package com.crear.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crear.auth.dto.RegisterResponse;
import com.crear.auth.dto.UniversityRegisterRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.crear.auth.service.AuthService;

@RestController
@RequestMapping("/api/hec")
@RequiredArgsConstructor
public class HecController {

    private final AuthService authService;

    @PostMapping("/register-university")
    public ResponseEntity<RegisterResponse> registerUniversity(@Valid @RequestBody UniversityRegisterRequest request) {
        RegisterResponse response = authService.registerUniversity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // getHecByUserid

}
