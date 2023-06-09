package com.example.githubapi.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class ApiController {

    private final GitHubService GITHUB_SERVICE;
    private final String USER_NOT_FOUND = "User not found on GitHub.";
    private final String WRONG_HEADER = "User found, but wrong header is being used (application/xml). Try again using application/json.";

    public ApiController(GitHubService gitHubService) {
        this.GITHUB_SERVICE = gitHubService;
    }

    @GetMapping("/repositories/{username}")
    public ResponseEntity<?> getUserRepositories(
            @PathVariable String username
    ) {
        try {
            Repository[] repositories = GITHUB_SERVICE.getUserRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(USER_NOT_FOUND );
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(WRONG_HEADER);
    }
}