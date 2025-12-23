package com.crear.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HecRegisterRequest {

    private String email;
    private String password;
    private String name;
    private String image;

    private String hecCode;
    private String headOfficeAddress;
    private String digitalSealCertPath;
}
