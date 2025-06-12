package com.example.demo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.exeptions.CurrencyApiException;

import java.net.URI;
import java.util.Map;

@Component
public class FixerApiClient {

    @Value("${api.fixer.key}")
    private String accessKey;

    private static final String BASE_URL = "http://data.fixer.io/api/latest";
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getLatestExchangeRates() {
        URI uri = UriComponentsBuilder
            .fromUriString(BASE_URL)
            .queryParam("access_key", accessKey)
            .build()
            .toUri();

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            if (response.getBody() == null || response.getStatusCode().isError()) {
                throw new CurrencyApiException("Failed to retrieve exchange rates from Fixer API");
            }

            return response.getBody();

        } catch (Exception e) {
            throw new CurrencyApiException("Error while calling Fixer API", e);
        }
    }
}
