package com.crear.dtos;

import com.crear.enums.DocumentStatus;
import com.crear.enums.RequestStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DegreeRequestVerifierDto {

  // Degree Request Info
  private UUID degreeRequestId;
  private String program;
  private String rollNumber;
  private Integer passingYear;
  private Double cgpa;
  private Instant requestDate;
  private RequestStatus status;
  private DocumentStatus documentStatus;
  private String remarks;

  // Student Info
  private String studentName;
  private String studentCnic;

  // University Info
  private String universityName;

  // IPFS info
  private String ipfsHash;
  private String ipfsProvider;

  // Blockchain Transaction Info
  private String transactionHash;
  private BigInteger blockNumber;
  private BigInteger gasUsed;
  private String contractAddress;
  private String blockchainStatus;

}
