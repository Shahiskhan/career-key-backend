package com.crear.dtos.degree_attestation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class IpfsUploadResponse {

  private String message;
  private String hash;
  private String publicUrl;

  // getters & setters
}
