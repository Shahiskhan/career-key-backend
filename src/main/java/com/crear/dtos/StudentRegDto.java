package com.crear.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class StudentRegDto {

    private String cnic;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String contactNumber;
    private String address;

}
