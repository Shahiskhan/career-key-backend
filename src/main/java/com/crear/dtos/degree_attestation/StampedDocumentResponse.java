package com.crear.dtos.degree_attestation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StampedDocumentResponse {
  private String fileName;
  private String contentType;
  private String base64;
}