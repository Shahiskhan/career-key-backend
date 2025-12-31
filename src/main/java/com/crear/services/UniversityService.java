package com.crear.services;

import com.crear.auth.dto.UniversityDto;
import com.crear.auth.model.User;
import com.crear.dtos.UniReg;
import java.util.List;
import java.util.UUID;

import com.crear.entities.University;

public interface UniversityService {
    University createUniversity(UniReg uniReg, User user);

    // get all register university name list
    List<UniversityDto> getAllUniversity();

    void validateUniversityCreation(UniReg dto);

    University getUniversityByUserId(UUID userId);

}
