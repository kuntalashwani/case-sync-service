package com.example.casesyncservice.client;

import com.example.casesyncservice.exception.CaseSyncException;
import com.example.casesyncservice.model.Cases;
import com.example.casesyncservice.model.StatusData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;

import static com.example.casesyncservice.client.URLConstants.*;
import static com.example.casesyncservice.util.AppConstants.BASIC;

@Slf4j
@Component
public class CaseServiceClient {

    @Value("${client.baseURL.caseStatusClient}")
    private String baseUrl;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${auth.username}")
    private String username;
    @Value(("${auth.password}"))
    private String password;

    public Cases fetchCasesFromExternalService(final String filterExpression) {
        log.info("Fetching Cases Data from External Service");
        String path = finalURL(FETCH_CASES_DATA, Map.of());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + path)
                .queryParamIfPresent("$filter", Optional.ofNullable(filterExpression));
        try {
            ResponseEntity<Cases> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                    new HttpEntity<>(buildAuthToken()), new ParameterizedTypeReference<>() {
                    }
            );
            return response.getBody();
        } catch (Exception e) {
            throw new CaseSyncException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Unable to fetch cases data from external service with response : %s", e));
        }
    }

    public void updateCaseStatus(final String caseId, final StatusData statusData) {
        String eTag = extractETagResponseHeader(caseId);
        log.info("Patching update to Closed status for completed cases with ETag: {}", eTag);
        String path = finalURL(UPDATE_CASES_DATA, Map.of(VARIABLE_CASE_ID, caseId));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + path);
        try {
            HttpHeaders headers = buildAuthToken();
            headers.set(HttpHeaders.IF_MATCH, eTag);
            restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH,
                    new HttpEntity<>(statusData, headers), new ParameterizedTypeReference<>() {
                    }
            );
        } catch (Exception e) {
            throw new CaseSyncException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Unable to patch cases data with response : %s", e));
        }
    }

    private String extractETagResponseHeader(final String caseId) {
        log.info("Extracting ETag from responseHeaders...");
        String path = finalURL(UPDATE_CASES_DATA, Map.of(VARIABLE_CASE_ID, caseId));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + path);
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                    new HttpEntity<>(buildAuthToken()), new ParameterizedTypeReference<>() {
                    }
            );
            return responseEntity.getHeaders().getFirst(HttpHeaders.ETAG);
        } catch (Exception e) {
            throw new CaseSyncException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Unable to patch cases data with response : %s", e));
        }
    }

    private HttpHeaders buildAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        String authHeader = username + ":" + password;
        byte[] authHeaderBytes = authHeader.getBytes();
        byte[] base64CredBytes = java.util.Base64.getEncoder().encode(authHeaderBytes);
        headers.set(HttpHeaders.AUTHORIZATION, BASIC + new String(base64CredBytes));
        return headers;
    }
}
