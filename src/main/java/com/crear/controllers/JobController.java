package com.crear.controllers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crear.dtos.StandardizedJob;
import com.crear.services.basic.JobFetchingService;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class JobController {

    private final JobFetchingService jobFetchingService;

    /**
     * Test endpoint to fetch jobs
     */
    @GetMapping("/fetch")
    public ResponseEntity<JobFetchResponse> fetchJobs(
            @RequestParam String keywords) {

        String[] keywordArray = keywords.split(",");

        List<StandardizedJob> jobs = jobFetchingService.fetchJobsFromAllAPIs(keywordArray);

        JobFetchResponse response = JobFetchResponse.builder()
                .totalJobs(jobs.size())
                .keywords(Arrays.asList(keywordArray))
                .jobs(jobs)
                .fetchedAt(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }
}

@Data
@Builder
class JobFetchResponse {
    private int totalJobs;
    private List<String> keywords;
    private List<StandardizedJob> jobs;
    private LocalDateTime fetchedAt;
}
