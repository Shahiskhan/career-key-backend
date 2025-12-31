package com.crear.auth.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class StudentRegisterRequest {

    // Basic User Fields
    private String email;
    private String password;
    private String name;
    private String image;

    // Student Specific Fields
    private String rollNumber;
    private String cnic;
    private LocalDate dateOfBirth;
    private String gender;
    private String contactNumber;
    private String address;

    private String universityName;
    private UUID universityId;

}
