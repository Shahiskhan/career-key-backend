package com.crear.auth.service;

import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import com.crear.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.crear.auth.dto.*;
import com.crear.auth.model.Provider;
import com.crear.auth.model.Role;
import com.crear.auth.model.User;
import com.crear.auth.repository.UserRepository;
import com.crear.dtos.*;
import com.crear.entities.University;
import com.crear.services.HecService;
import com.crear.services.StudentService;
import com.crear.services.UniversityService;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentService studentService;
    private final UniversityService universityService;
    private final HecService hecService;

    // Generic registration
    public RegisterResponse register(RegisterRequest request) {
        if (request == null || request.getEmail() == null || request.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (userRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        String encoded = null;
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            encoded = passwordEncoder.encode(request.getPassword());
        }

        User user = User.builder()
                .email(request.getEmail().trim().toLowerCase())
                .name(request.getName())
                .provider(Provider.LOCAL)
                .password(encoded)
                .image(request.getImage())
                .roles(new HashSet<>())
                .enabled(true)
                .build();

        // Assign default role if needed (base registration doesn't specify role)
        user.getRoles().add(Role.STUDENT);

        User saved = userRepository.save(user);

        return toResponse(saved);
    }

    // Student registration
    public RegisterResponse registerStudent(StudentRegisterRequest req) {
        validateUser(req.getEmail());

        try {
            // First create student record
            StudentRegDto dto = new StudentRegDto();
            dto.setCnic(req.getCnic());
            dto.setFullName(req.getName());
            dto.setDateOfBirth(req.getDateOfBirth());
            dto.setGender(req.getGender());
            dto.setContactNumber(req.getContactNumber());
            dto.setAddress(req.getAddress());

            // Try to create student first
            // studentService.validateStudentCreation(dto);

        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot register student: " + e.getMessage());
        }

        // Only create user if student can be created
        User user = createBaseUser(req.getEmail(), req.getPassword(), req.getName(), req.getImage());
        user.getRoles().add(Role.STUDENT);
        User savedUser = userRepository.save(user);

        // Now actually create student with user
        StudentRegDto dto = new StudentRegDto();
        dto.setRollnumber(req.getRollNumber());
        dto.setCnic(req.getCnic());
        dto.setFullName(req.getName());
        dto.setDateOfBirth(req.getDateOfBirth());
        dto.setGender(req.getGender());
        dto.setContactNumber(req.getContactNumber());
        dto.setAddress(req.getAddress());
        dto.setUniversityId(req.getUniversityId());
        studentService.createStudent(dto, savedUser);

        return toResponse(savedUser);
    }

    // University registration - CHANGED
    public RegisterResponse registerUniversity(UniversityRegisterRequest req) {
        validateUser(req.getEmail());

        // First validate if university can be created
        try {
            UniReg dto = new UniReg();
            dto.setName(req.getUniversityName());
            dto.setCity(req.getCity());
            dto.setCharterNumber(req.getCharterNumber());
            dto.setIssuingAuthority(req.getIssuingAuthority());
            dto.setHecRecognized(req.getHecRecognized());

            // Validate university creation before creating user
            universityService.validateUniversityCreation(dto);

        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot register university: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid university data: " + e.getMessage());
        }

        // Only create user if university can be created
        User user = createBaseUser(req.getEmail(), req.getPassword(), req.getName(), req.getImage());
        user.getRoles().add(Role.UNIVERSITY);
        User savedUser = userRepository.save(user);

        // Now actually create university with user
        UniReg dto = new UniReg();
        dto.setName(req.getUniversityName());
        dto.setCity(req.getCity());
        dto.setCharterNumber(req.getCharterNumber());
        dto.setIssuingAuthority(req.getIssuingAuthority());
        dto.setHecRecognized(req.getHecRecognized());
        University u = universityService.createUniversity(dto, savedUser);
        RegisterResponse rb = toResponse(savedUser);
        rb.setUniversityId(u.getId());
        return rb;
    }

    // HEC registration - CHANGED
    public RegisterResponse registerHec(HecRegisterRequest req) {
        validateUser(req.getEmail());

        // First validate if HEC can be created
        try {
            HecRegDto dto = new HecRegDto();
            dto.setHecCode(req.getHecCode());
            dto.setName(req.getName());
            dto.setHeadOfficeAddress(req.getHeadOfficeAddress());
            dto.setDigitalSealCertPath(req.getDigitalSealCertPath());

            // Validate HEC creation before creating user
            hecService.validateHecCreation(dto);

        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot register HEC: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid HEC data: " + e.getMessage());
        }

        // Only create user if HEC can be created
        User user = createBaseUser(req.getEmail(), req.getPassword(), req.getName(), req.getImage());
        user.getRoles().add(Role.HEC);
        User savedUser = userRepository.save(user);

        // Now actually create HEC with user
        HecRegDto dto = new HecRegDto();
        dto.setHecCode(req.getHecCode());
        dto.setName(req.getName());
        dto.setHeadOfficeAddress(req.getHeadOfficeAddress());
        dto.setDigitalSealCertPath(req.getDigitalSealCertPath());
        hecService.createHec(dto, savedUser);

        return toResponse(savedUser);
    }

    // --------------------
    private void validateUser(String email) {
        if (email == null || email.isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email required");

        if (userRepository.existsByEmail(email.trim().toLowerCase()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
    }

    private User createBaseUser(String email, String password, String name, String image) {
        return User.builder()
                .email(email.trim().toLowerCase())
                .password(passwordEncoder.encode(password))
                .name(name)
                .image(image)
                .provider(Provider.LOCAL)
                .roles(new HashSet<>())
                .enabled(true)
                .build();
    }

    private RegisterResponse toResponse(User savedUser) {
        return RegisterResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .image(savedUser.getImage())
                .enabled(savedUser.isEnabled())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }
}