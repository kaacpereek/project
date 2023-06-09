package com.example.githubapi.project;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubService {

    @Value("${github.api.url}")
    private String apiUrl;

    public Repository[] getUserRepositories(String username) {
        String url = apiUrl + "/users/" + username + "/repos?type=all";
        ResponseEntity<Repository[]> response = sendRequest(url, MediaType.APPLICATION_JSON);
        return response.getBody();
    }

    private ResponseEntity<Repository[]> sendRequest(String url, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(mediaType));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, HttpMethod.GET, entity, Repository[].class);
    }
}
