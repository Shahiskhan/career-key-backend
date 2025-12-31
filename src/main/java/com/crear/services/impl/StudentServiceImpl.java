package com.crear.services.impl;

import com.crear.auth.model.User;
import com.crear.dtos.StudentRegDto;
import com.crear.entities.Student;
import com.crear.entities.University;
import com.crear.repositories.StudentRepository;
import com.crear.repositories.UniversityRepository;
import com.crear.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UniversityRepository universityRepository;

    @Override
    public Student createStudent(StudentRegDto dto, User user) {

        University uni = universityRepository.findById(dto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found"));

        Student student = Student.builder()

                .user(user)
                .cnic(dto.getCnic())
                .rollNumber(dto.getRollnumber())
                .fullName(dto.getFullName())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .contactNumber(dto.getContactNumber())
                .address(dto.getAddress())
                .university(uni)
                .graduated(false)
                .build();

        return studentRepository.save(student);
    }

    @Override
    public Student getBy(String studentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBy'");
    }
}
