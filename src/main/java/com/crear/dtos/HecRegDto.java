package com.crear.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HecRegDto {

    private String hecCode; // HEC code
    private String name; // Name of HEC
    private String headOfficeAddress; // Address of head office
    private String digitalSealCertPath; // Path to digital seal certificate
}