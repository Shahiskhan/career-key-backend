package com.crear.dtos.degree_attestation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StampRequestDto {

  private String studentName;
  private String rollNumber;
  private String universityName;
  private String program;
}
