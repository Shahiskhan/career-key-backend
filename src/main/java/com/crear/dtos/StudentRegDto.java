package com.crear.dtos;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class StudentRegDto {

    private String rollnumber;
    private String cnic;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String contactNumber;
    private String address;
    private UUID universityId;

}
