package com.crear.services;

import com.crear.auth.model.User;
import com.crear.dtos.StudentRegDto;
import com.crear.entities.Student;

public interface StudentService {
    Student createStudent(StudentRegDto dto, User user);
}
