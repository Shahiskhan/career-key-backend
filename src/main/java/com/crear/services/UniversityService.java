package com.crear.services;

import com.crear.auth.model.User;
import com.crear.dtos.UniReg;
import java.util.List;

import com.crear.entities.University;

public interface UniversityService {
    University createUniversity(UniReg uniReg, User user);

    // get all register university name list
    List<String> getAllUniversityNames();

    void validateUniversityCreation(UniReg dto);

}
