package com.example.demo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.exeptions.IpNotFoundException;

import org.springframework.core.ParameterizedTypeReference;
import java.net.URI;
import java.util.Map;

@Component
public class IpApiClient {

    @Value("${ipapi.access.key}")
    private String accessKey;

    private static final String BASE_URL = "https://api.ipapi.com/api/";
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getLocationInfo(String ip) {
       URI uri = UriComponentsBuilder
            .fromUriString(BASE_URL + ip)
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
                throw new IpNotFoundException("Failed to retrieve location info for IP: " + ip);
            }

            return response.getBody();

        } catch (Exception e) {
            throw new IpNotFoundException("Error while calling IPApi for IP: " + ip, e);
        }
    }
}
