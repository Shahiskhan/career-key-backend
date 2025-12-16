package com.crear.services.basic;

import java.net.URLEncoder;
import org.springframework.http.HttpHeaders;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crear.dtos.StandardizedJob;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobFetchingService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    // API Configuration (from application.properties)
    @Value("${adzuna.api.id:YOUR_ID}")
    private String adzunaAppId;

    @Value("${adzuna.api.key:YOUR_KEY}")
    private String adzunaAppKey;

    @Value("${jsearch.api.key:YOUR_RAPIDAPI_KEY}")
    private String jSearchApiKey;

    @Value("${findwork.api.key:YOUR_FINDWORK_KEY}")
    private String findworkApiKey;

    public JobFetchingService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Main method to fetch jobs from multiple APIs
     * Returns unique, standardized job list
     */
    public List<StandardizedJob> fetchJobsFromAllAPIs(String[] keywords) {

        log.info("Fetching jobs for keywords: {}", Arrays.toString(keywords));

        // Create a Set to store unique jobs (prevent duplicates)
        Set<StandardizedJob> uniqueJobs = new HashSet<>();

        // Fetch from all APIs in parallel
        CompletableFuture<List<StandardizedJob>> remotiveJobs = CompletableFuture
                .supplyAsync(() -> fetchFromRemotive(keywords));

        CompletableFuture<List<StandardizedJob>> adzunaJobs = CompletableFuture
                .supplyAsync(() -> fetchFromAdzuna(keywords));

        CompletableFuture<List<StandardizedJob>> jSearchJobs = CompletableFuture
                .supplyAsync(() -> fetchFromJSearch(keywords));

        CompletableFuture<List<StandardizedJob>> arbeitNowJobs = CompletableFuture
                .supplyAsync(() -> fetchFromArbeitNow(keywords));

        CompletableFuture<List<StandardizedJob>> findworkJobs = CompletableFuture
                .supplyAsync(() -> fetchFromFindwork(keywords));

        CompletableFuture<List<StandardizedJob>> careerjetJobs = CompletableFuture
                .supplyAsync(() -> fetchFromCareerjet(keywords));

        // Wait for all to complete
        CompletableFuture.allOf(
                remotiveJobs, adzunaJobs, jSearchJobs,
                arbeitNowJobs, findworkJobs, careerjetJobs).join();

        // Collect results (ignore failed APIs)
        try {
            uniqueJobs.addAll(remotiveJobs.get());
        } catch (Exception e) {
            log.warn("Remotive API failed: {}", e.getMessage());
        }

        try {
            uniqueJobs.addAll(adzunaJobs.get());
        } catch (Exception e) {
            log.warn("Adzuna API failed: {}", e.getMessage());
        }

        try {
            uniqueJobs.addAll(jSearchJobs.get());
        } catch (Exception e) {
            log.warn("JSearch API failed: {}", e.getMessage());
        }

        try {
            uniqueJobs.addAll(arbeitNowJobs.get());
        } catch (Exception e) {
            log.warn("ArbeitNow API failed: {}", e.getMessage());
        }

        try {
            uniqueJobs.addAll(findworkJobs.get());
        } catch (Exception e) {
            log.warn("Findwork API failed: {}", e.getMessage());
        }

        try {
            uniqueJobs.addAll(careerjetJobs.get());
        } catch (Exception e) {
            log.warn("Careerjet API failed: {}", e.getMessage());
        }

        log.info("Total unique jobs fetched: {}", uniqueJobs.size());

        return new ArrayList<>(uniqueJobs);
    }

    /**
     * 1. Remotive API (100% Free, No API Key)
     */
    private List<StandardizedJob> fetchFromRemotive(String[] keywords) {
        List<StandardizedJob> jobs = new ArrayList<>();

        try {
            String keywordQuery = String.join(" ", keywords);
            String url = "https://remotive.com/api/remote-jobs?search="
                    + URLEncoder.encode(keywordQuery, "UTF-8");

            log.info("Calling Remotive API: {}", url);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode jobsArray = root.get("jobs");

                if (jobsArray != null && jobsArray.isArray()) {
                    for (JsonNode jobNode : jobsArray) {
                        StandardizedJob job = new StandardizedJob();
                        job.setJobId("remotive_" + jobNode.get("id").asText());
                        job.setTitle(jobNode.get("title").asText());
                        job.setCompany(jobNode.get("company_name").asText());
                        job.setLocation(jobNode.path("candidate_required_location").asText("Remote"));
                        job.setDescription(jobNode.path("description").asText());
                        job.setUrl(jobNode.get("url").asText());
                        job.setJobType(jobNode.path("job_type").asText("full_time"));
                        job.setCategory(jobNode.path("category").asText());
                        job.setSalary(jobNode.path("salary").asText());
                        job.setPostedDate(jobNode.path("publication_date").asText());
                        job.setSource("Remotive");

                        jobs.add(job);
                    }
                }
            }

            log.info("Remotive: Fetched {} jobs", jobs.size());

        } catch (Exception e) {
            log.error("Error fetching from Remotive: {}", e.getMessage());
        }

        return jobs;
    }

    /**
     * 2. Adzuna API (Free 500 calls/day)
     */
    private List<StandardizedJob> fetchFromAdzuna(String[] keywords) {
        List<StandardizedJob> jobs = new ArrayList<>();

        try {
            String keywordQuery = String.join(" ", keywords);
            String url = String.format(
                    "https://api.adzuna.com/v1/api/jobs/us/search/1?app_id=%s&app_key=%s&what=%s&results_per_page=50",
                    adzunaAppId, adzunaAppKey, URLEncoder.encode(keywordQuery, "UTF-8"));

            log.info("Calling Adzuna API: {}", url);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode resultsArray = root.get("results");

                if (resultsArray != null && resultsArray.isArray()) {
                    for (JsonNode jobNode : resultsArray) {
                        StandardizedJob job = new StandardizedJob();
                        job.setJobId("adzuna_" + jobNode.get("id").asText());
                        job.setTitle(jobNode.get("title").asText());
                        job.setCompany(jobNode.path("company").path("display_name").asText());
                        job.setLocation(jobNode.path("location").path("display_name").asText());
                        job.setDescription(jobNode.path("description").asText());
                        job.setUrl(jobNode.get("redirect_url").asText());
                        job.setJobType(jobNode.path("contract_type").asText());
                        job.setCategory(jobNode.path("category").path("label").asText());

                        // Salary
                        String salary = "";
                        if (jobNode.has("salary_min") && jobNode.has("salary_max")) {
                            salary = "$" + jobNode.get("salary_min").asInt() +
                                    " - $" + jobNode.get("salary_max").asInt();
                        }
                        job.setSalary(salary);

                        job.setPostedDate(jobNode.path("created").asText());
                        job.setSource("Adzuna");

                        jobs.add(job);
                    }
                }
            }

            log.info("Adzuna: Fetched {} jobs", jobs.size());

        } catch (Exception e) {
            log.error("Error fetching from Adzuna: {}", e.getMessage());
        }

        return jobs;
    }

    /**
     * 3. JSearch API (RapidAPI - Free 100 requests/month)
     */
    private List<StandardizedJob> fetchFromJSearch(String[] keywords) {
        List<StandardizedJob> jobs = new ArrayList<>();

        try {
            String keywordQuery = String.join(" ", keywords);
            String url = "https://jsearch.p.rapidapi.com/search?query="
                    + URLEncoder.encode(keywordQuery, "UTF-8") + "&page=1&num_pages=1";

            log.info("Calling JSearch API: {}", url);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", jSearchApiKey);
            headers.set("X-RapidAPI-Host", "jsearch.p.rapidapi.com");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode dataArray = root.get("data");

                if (dataArray != null && dataArray.isArray()) {
                    for (JsonNode jobNode : dataArray) {
                        StandardizedJob job = new StandardizedJob();
                        job.setJobId("jsearch_" + jobNode.get("job_id").asText());
                        job.setTitle(jobNode.get("job_title").asText());
                        job.setCompany(jobNode.path("employer_name").asText());
                        job.setLocation(jobNode.path("job_city").asText() + ", " +
                                jobNode.path("job_country").asText());
                        job.setDescription(jobNode.path("job_description").asText());
                        job.setUrl(jobNode.get("job_apply_link").asText());
                        job.setJobType(jobNode.path("job_employment_type").asText());
                        job.setSalary(jobNode.path("job_salary").asText());
                        job.setPostedDate(jobNode.path("job_posted_at_datetime_utc").asText());
                        job.setSource("JSearch");

                        jobs.add(job);
                    }
                }
            }

            log.info("JSearch: Fetched {} jobs", jobs.size());

        } catch (Exception e) {
            log.error("Error fetching from JSearch: {}", e.getMessage());
        }

        return jobs;
    }

    /**
     * 4. ArbeitNow API (100% Free, No API Key)
     */
    private List<StandardizedJob> fetchFromArbeitNow(String[] keywords) {
        List<StandardizedJob> jobs = new ArrayList<>();

        try {
            String url = "https://www.arbeitnow.com/api/job-board-api";

            log.info("Calling ArbeitNow API: {}", url);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode dataArray = root.get("data");

                if (dataArray != null && dataArray.isArray()) {
                    for (JsonNode jobNode : dataArray) {
                        // Filter by keywords
                        String title = jobNode.path("title").asText().toLowerCase();
                        boolean matches = Arrays.stream(keywords)
                                .anyMatch(kw -> title.contains(kw.toLowerCase()));

                        if (matches) {
                            StandardizedJob job = new StandardizedJob();
                            job.setJobId("arbeitnow_" + jobNode.get("slug").asText());
                            job.setTitle(jobNode.get("title").asText());
                            job.setCompany(jobNode.path("company_name").asText());
                            job.setLocation(jobNode.path("location").asText());
                            job.setDescription(jobNode.path("description").asText());
                            job.setUrl(jobNode.get("url").asText());
                            job.setJobType(jobNode.path("job_types").toString());
                            job.setPostedDate(jobNode.path("created_at").asText());
                            job.setSource("ArbeitNow");

                            jobs.add(job);
                        }
                    }
                }
            }

            log.info("ArbeitNow: Fetched {} jobs", jobs.size());

        } catch (Exception e) {
            log.error("Error fetching from ArbeitNow: {}", e.getMessage());
        }

        return jobs;
    }

    /**
     * 5. Findwork API (Requires API Key)
     */
    private List<StandardizedJob> fetchFromFindwork(String[] keywords) {
        List<StandardizedJob> jobs = new ArrayList<>();

        try {
            String keywordQuery = String.join(" ", keywords);
            String url = "https://findwork.dev/api/jobs/?search="
                    + URLEncoder.encode(keywordQuery, "UTF-8");

            log.info("Calling Findwork API: {}", url);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Token " + findworkApiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode resultsArray = root.get("results");

                if (resultsArray != null && resultsArray.isArray()) {
                    for (JsonNode jobNode : resultsArray) {
                        StandardizedJob job = new StandardizedJob();
                        job.setJobId("findwork_" + jobNode.get("id").asText());
                        job.setTitle(jobNode.get("role").asText());
                        job.setCompany(jobNode.path("company_name").asText());
                        job.setLocation(jobNode.path("location").asText());
                        job.setDescription(jobNode.path("text").asText());
                        job.setUrl(jobNode.get("url").asText());
                        job.setJobType(jobNode.path("employment_type").asText());
                        job.setPostedDate(jobNode.path("date_posted").asText());
                        job.setSource("Findwork");

                        jobs.add(job);
                    }
                }
            }

            log.info("Findwork: Fetched {} jobs", jobs.size());

        } catch (Exception e) {
            log.error("Error fetching from Findwork: {}", e.getMessage());
        }

        return jobs;
    }

    /**
     * 6. Careerjet API (Free tier available)
     */
    private List<StandardizedJob> fetchFromCareerjet(String[] keywords) {
        List<StandardizedJob> jobs = new ArrayList<>();

        try {
            // Note: Careerjet requires partner account
            // This is a placeholder implementation
            log.info("Careerjet: Skipping (requires partner account)");

        } catch (Exception e) {
            log.error("Error fetching from Careerjet: {}", e.getMessage());
        }

        return jobs;
    }
}
