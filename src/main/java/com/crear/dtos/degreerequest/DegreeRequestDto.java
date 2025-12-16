package com.crear.dtos.degreerequest;

import com.crear.enums.RequestStatus;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DegreeRequestDto {
    private String studentName;
    private String universityName;
    private String program;
    private String rollNumber;
    private Integer passingYear;
    private  boolean stampedByHec;
    private Double cgpa;
    private String documentPath;
    private Instant requestDate;
    private RequestStatus status;
    private String remarks;
}