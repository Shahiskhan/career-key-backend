package com.crear.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.crear.auth.dto.HecRegisterRequest;
import com.crear.auth.repository.UserRepository;
import com.crear.auth.service.AuthService;
import com.crear.config.HecAutoRegistrationProperties;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(1) // First to run
public class DataSeeder implements CommandLineRunner {

  private final AuthService authService;
  private final UserRepository userRepository;
  private final HecAutoRegistrationProperties properties;

  @Override
  @Transactional
  public void run(String... args) {
    seedHecAdmin();
    // You can add more seed data here
    // seedUniversities();
    // seedStudents();
  }

  private void seedHecAdmin() {

    if (!properties.isEnabled()) {
      log.info("HEC auto-registration is disabled");
      return;
    }

    if (userRepository.existsByEmail(properties.getEmail())) {
      log.info("HEC admin already exists: {}", properties.getEmail());
      return;
    }

    try {
      HecRegisterRequest request = HecRegisterRequest.builder()
          .email(properties.getEmail())
          .password(properties.getPassword())
          .name(properties.getName())
          .hecCode(properties.getHecCode())
          .headOfficeAddress(properties.getHeadOffice())
          .digitalSealCertPath(properties.getDigitalSealPath())
          .build();

      var response = authService.registerHec(request);

      log.info("✅ HEC admin seeded successfully: {}", response.getEmail());

    } catch (Exception e) {
      log.error("❌ Failed to seed HEC admin: {}", e.getMessage());
      // Continue application startup even if seeding fails
    }
  }
}