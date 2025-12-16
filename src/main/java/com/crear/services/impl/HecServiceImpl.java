package com.crear.services.impl;

import com.crear.auth.model.User;
import com.crear.dtos.HecRegDto;
import com.crear.entities.Hec;
import com.crear.repositories.HecRepository;
import com.crear.services.HecService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HecServiceImpl implements HecService {

    private final HecRepository hecRepository;

    @Override
    public Hec createHec(HecRegDto dto, User user) {

        Hec hec = Hec.builder()
                .user(user)
                .hecCode(dto.getHecCode())
                .name(dto.getName())
                .headOfficeAddress(dto.getHeadOfficeAddress())
                .digitalSealCertPath(dto.getDigitalSealCertPath())
                .build();

        return hecRepository.save(hec);
    }
}
