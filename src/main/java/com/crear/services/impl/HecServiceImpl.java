package com.crear.services.impl;

import com.crear.auth.model.User;
import com.crear.dtos.HecRegDto;
import com.crear.entities.Hec;
import com.crear.exceptions.ResourceNotFoundException;
import com.crear.repositories.HecRepository;
import com.crear.services.HecService;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Override
    public void validateHecCreation(HecRegDto dto) {
        if (dto.getHecCode() == null || dto.getHecCode().isBlank()) {
            throw new ResourceNotFoundException("HEC code is required");
        }

        if (hecRepository.existsByHecCode(dto.getHecCode())) {
            throw new ResourceNotFoundException("HEC with this code already exists");
        }
    }

    @Override
    public HecRegDto getHecByUser(User user) {

        Hec hec = hecRepository.findByUser(user)
                .orElseThrow(() -> new UsernameNotFoundException("University not found for user id: " + user.getId()));
        return HecRegDto.builder()
                .hecCode(hec.getHecCode())
                .name(hec.getName())
                .headOfficeAddress(hec.getHeadOfficeAddress())
                .digitalSealCertPath(hec.getDigitalSealCertPath())
                .build();
    }

}
