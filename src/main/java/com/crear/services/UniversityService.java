package com.crear.services;

import com.crear.auth.model.User;
import com.crear.dtos.UniReg;
import com.crear.entities.University;

public interface UniversityService {
    University createUniversity(UniReg uniReg, User user);
}
