package com.crear.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "jobId" })
public class StandardizedJob {

    private String jobId;
    private String title;
    private String company;
    private String location;
    private String description;
    private String url;
    private String jobType; // full_time
    private String category;
    private String salary;
    private String postedDate;
    private String source; // API source name

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fetchedAt = LocalDateTime.now();
}
