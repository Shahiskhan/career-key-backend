package com.crear.dtos.degree_attestation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateResultRequest {
  private String studentId;
  private String newIpfsHash;
}
