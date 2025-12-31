package com.crear.auth.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class UniversityRegisterRequest {

    // Basic User Fields

    private String email;
    private String password;
    private String name;
    private String image;

    // University fields
    private String universityName;
    private String city;
    private String charterNumber;
    private String issuingAuthority;
    private Boolean hecRecognized;

}
