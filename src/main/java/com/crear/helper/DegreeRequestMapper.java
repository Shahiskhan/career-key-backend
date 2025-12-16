package com.crear.helper;

import com.crear.dtos.degreerequest.DegreeRequestCreateDto;
import com.crear.dtos.degreerequest.DegreeRequestDto;
import com.crear.entities.DegreeRequest;
import com.crear.entities.Student;
import com.crear.entities.University;

public class DegreeRequestMapper {

    // Entity → DTO
    public static DegreeRequestDto toDto(DegreeRequest entity) {
        if (entity == null)
            return null;

        return DegreeRequestDto.builder()
                .studentName(entity.getStudent() != null ? entity.getStudent().getFullName() : null)
                .universityName(entity.getUniversity() != null ? entity.getUniversity().getName() : null)
                .program(entity.getProgram())
                .rollNumber(entity.getRollNumber())
                .passingYear(entity.getPassingYear())
                .cgpa(entity.getCgpa())
                .requestDate(entity.getRequestDate())
                .status(entity.getStatus())
                .remarks(entity.getRemarks())
                .build();
    }

    // DTO → Entity (Create)
    public static DegreeRequest toEntity(DegreeRequestCreateDto dto, Student student, University university) {
        if (dto == null)
            return null;

        return DegreeRequest.builder()
                .student(student)
                .university(university)
                .program(dto.getProgram())
                .rollNumber(dto.getRollNumber())
                .passingYear(dto.getPassingYear())
                .cgpa(dto.getCgpa())
                .remarks(dto.getRemarks())
                .build();
    }
}
