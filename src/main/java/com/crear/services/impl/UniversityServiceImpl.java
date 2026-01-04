package com.crear.services.impl;

import java.util.List;
import java.util.UUID;

import com.crear.dtos.UniReg;
import com.crear.entities.University;
import com.crear.exceptions.ResourceNotFoundException;
import com.crear.auth.dto.UniversityDto;
import com.crear.auth.model.User;
import com.crear.repositories.UniversityRepository;
import com.crear.repositories.repohelpers.UniversityStats;
import com.crear.services.UniversityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j // 🔹 Adds the SLF4J logger
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    @Override
    public University createUniversity(UniReg uniReg, User user) {
        University university = University.builder()
                .user(user)
                .name(uniReg.getName())
                .city(uniReg.getCity())
                .charterNumber(uniReg.getCharterNumber())
                .issuingAuthority(uniReg.getIssuingAuthority())
                .hecRecognized(uniReg.getHecRecognized() != null ? uniReg.getHecRecognized() : false)
                .build();

        University saved = universityRepository.save(university);
        log.info("Created new university: {} with ID {}", saved.getName(), saved.getId());
        return saved;
    }

    @Override
    public List<UniversityDto> getAllUniversity() {
        return universityRepository.findAll()
                .stream()
                .map(university -> new UniversityDto(
                        university.getId(),
                        university.getName()))
                .toList();
    }

    public void validateUniversityCreation(UniReg dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            log.warn("University creation failed: name is missing");
            throw new ResourceNotFoundException("University name is required");
        }

        if (dto.getCharterNumber() == null || dto.getCharterNumber().isBlank()) {
            log.warn("University creation failed: charter number is missing");
            throw new ResourceNotFoundException("Charter number is required");
        }

        if (universityRepository.existsByCharterNumber(dto.getCharterNumber())) {
            log.warn("University creation failed: charter number {} already exists", dto.getCharterNumber());
            throw new ResourceNotFoundException("University with charter number already exists");
        }

        // Additional validation if needed
        if (dto.getHecRecognized() != null && dto.getHecRecognized()) {
            log.info("HEC recognized university being validated: {}", dto.getName());
        }
    }

    @Override
    public UniversityDto getUniversityByUserId(UUID userId) {
        log.info("Fetching university for userId: {}", userId);

        University university = universityRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("University not found for userId: {}", userId);
                    return new RuntimeException("University not found");
                });

        log.info("Found university: {} (ID: {}) for userId: {}", university.getName(), university.getId(), userId);

        UniversityStats stats = universityRepository.fetchStats(university.getId());
        log.info("Fetched university stats: totalStudents={}, totalDegrees={}, pendingDegreeRequests={}",
                stats.getTotalStudents(), stats.getTotalDegrees(), stats.getPendingRequests());

        return UniversityDto.builder()
                .id(university.getId())
                .name(university.getName())
                .city(university.getCity())
                .charterNumber(university.getCharterNumber())
                .issuingAuthority(university.getIssuingAuthority())
                .hecRecognized(university.getHecRecognized())
                .hecId(university.getHec() != null ? university.getHec().getId() : null)
                .hecName(university.getHec() != null ? university.getHec().getName() : null)

                // 🔥 Uncomment if you want stats in DTO
                // .totalStudents((int) stats.getTotalStudents())
                // .totalDegrees((int) stats.getTotalDegrees())
                // .pendingDegreeRequests((int) stats.getPendingRequests())

                .build();
    }
}
