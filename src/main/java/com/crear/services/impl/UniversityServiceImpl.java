package com.crear.services.impl;

import java.util.List;
import com.crear.dtos.UniReg;
import com.crear.entities.University;
import com.crear.exceptions.ResourceNotFoundException;
import com.crear.auth.model.User;
import com.crear.repositories.UniversityRepository;
import com.crear.services.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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

        return universityRepository.save(university);
    }

    @Override
    public List<String> getAllUniversityNames() {
        // return universityRepository.findAllUniversityNames();
        return null;
    }

    public void validateUniversityCreation(UniReg dto) {
        // Validate required fields
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new ResourceNotFoundException("University name is required");
        }

        if (dto.getCharterNumber() == null || dto.getCharterNumber().isBlank()) {
            throw new ResourceNotFoundException("Charter number is required");
        }

        // Check if university already exists
        if (universityRepository.existsByCharterNumber(dto.getCharterNumber())) {
            throw new ResourceNotFoundException("University with charter number already exists");
        }

        // Add any other validation logic
        if (dto.getHecRecognized() != null && dto.getHecRecognized()) {
            // Validate HEC recognition if needed
        }
    }
}
