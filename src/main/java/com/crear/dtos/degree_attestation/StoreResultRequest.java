package com.crear.dtos.degree_attestation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreResultRequest {
  private String studentId;
  private String cnic;
  private String ipfsHash;
}
