package com.crear.dtos.degree_attestation;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreResultResponse {

  // Student basic info

  private String studentCnic;
  private String rollNumber;

  // DegreeRequest info
  private UUID degreeRequestId;
  private String ipfsHash;

  // Blockchain info
  private String transactionHash;
  private String blockNumber;
  private String status;
}
