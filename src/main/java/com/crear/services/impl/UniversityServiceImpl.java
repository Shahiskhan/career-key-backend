package com.crear.services.impl;

import com.crear.dtos.UniReg;
import com.crear.entities.University;
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
}
