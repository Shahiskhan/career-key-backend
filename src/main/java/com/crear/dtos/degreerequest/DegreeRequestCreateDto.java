package com.crear.dtos.degreerequest;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DegreeRequestCreateDto {
    private String program;
    private String rollNumber;
    private Integer passingYear;

    private Double cgpa;
    private String remarks;
    // private List<String> documentPaths;

    // document paths ya names

}
