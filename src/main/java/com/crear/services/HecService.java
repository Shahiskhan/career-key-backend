package com.crear.services;

import com.crear.auth.model.User;
import com.crear.dtos.HecRegDto;
import com.crear.entities.Hec;

public interface HecService {
    Hec createHec(HecRegDto dto, User user);

    void validateHecCreation(HecRegDto dto);
}
