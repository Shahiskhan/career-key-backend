package com.crear.auth.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniversityDto {

  private UUID id;

  private String name;

  private String city;

  // important identifiers
  private String charterNumber;
  private String issuingAuthority;

  // status
  private Boolean hecRecognized;

  // relations (ONLY IDs / names, not full objects)
  private UUID hecId;
  private String hecName;

  // optional stats (dashboard ke liye useful)
  private Integer totalStudents;
  private Integer totalDegrees;
  private Integer pendingDegreeRequests;

  // minimal constructor (already good 👍)
  public UniversityDto(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

}
