
package com.communityhealth.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Component
public class ApiMedicClient {

    private static final String BASE_URL_SANDBOX = "https://sandbox-healthservice.priaid.ch";
    private static final String BASE_URL_LIVE    = "https://healthservice.priaid.ch";

    private final RestTemplate restTemplate;
    private final String username;   // from application.yml / secrets
    private final String password;   // from application.yml / secrets
    private final boolean useSandbox;

    private String authToken;

    public ApiMedicClient(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${apimedic.username}") String username,
            @Value("${apimedic.password}") String password,
            @Value("${apimedic.sandbox:true}") boolean useSandbox
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.username = username;
        this.password = password;
        this.useSandbox = useSandbox;
    }

    private String getBaseUrl() {
        return useSandbox ? BASE_URL_SANDBOX : BASE_URL_LIVE;
    }

    private String getAuthToken() {
        if (authToken == null) {
            String uri = getBaseUrl() + "/login";
            String credentials = username + ":" + password;
            String auth = Base64.getEncoder().encodeToString(credentials.getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + auth);

            ResponseEntity<String> response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    new HttpEntity<>(headers),
                    String.class
            );
            authToken = response.getBody();  // e.g. "xyz-token-123"
        }
        return authToken;
    }

    public List<Map<String, Object>> fetchDiagnosis(String symptoms, LocalDate dateOfBirth, String gender) {
        String uri = getBaseUrl() + "/diagnosis" +
                "?symptoms=" + URLEncoder.encode(symptoms, StandardCharsets.UTF_8) +
                "&gender=" + gender +
                "&year_of_birth=" + dateOfBirth +
                "&token=" + getAuthToken() +
                "&format=json&language=en-gb";  // adjust language

        try {
            ResponseEntity<List> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            //noinspection unchecked
            return (List<Map<String, Object>>) response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                authToken = null;
                return fetchDiagnosis(symptoms, dateOfBirth, gender);
            }
            throw e;
        }
    }
}
