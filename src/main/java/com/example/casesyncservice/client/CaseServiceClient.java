package com.example.casesyncservice.client;

import com.example.casesyncservice.exception.CaseSyncException;
import com.example.casesyncservice.model.Cases;
import com.example.casesyncservice.model.StatusData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;

import static com.example.casesyncservice.client.URLConstants.*;

@Slf4j
@Component
public class CaseServiceClient {

    @Value("${client.baseURL.caseStatusClient}")
    private String baseUrl;
    @Autowired
    private RestTemplate restTemplate;

    public Cases fetchCasesFromExternalService(final String filterExpression) {
        log.info("Fetching Cases Data from External Service");
        String path = finalURL(FETCH_CASES_DATA, Map.of());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + path)
                .queryParamIfPresent("$filter", Optional.ofNullable(filterExpression));
        try {
            ResponseEntity<Cases> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                    null, new ParameterizedTypeReference<>() {
                    }
            );
            return response.getBody();
        } catch (Exception e) {
            throw new CaseSyncException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Unable to fetch cases data from external service with response : %s", e));
        }
    }

    public void updateCaseStatus(final StatusData statusData) {
        log.info("Patching update to Closed status for completed cases..");
        String path = finalURL(UPDATE_CASES_DATA, Map.of());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + path);
        try {
            restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH,
                    new HttpEntity<>(statusData), new ParameterizedTypeReference<>() {
                    }
            );
        } catch (Exception e) {
            throw new CaseSyncException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Unable to patch cases data with response : %s", e));
        }
    }
}
