package com.example.demo.client;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.exeptions.CountryNotFoundException;

@Component
public class CountriesClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getCountryInfo(String countryCode) {
        String url = "https://restcountries.com/v2/alpha/" + countryCode;

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            if (response.getBody() == null || response.getStatusCode().isError()) {
                throw new CountryNotFoundException("No information was found for the country with code: " + countryCode);
            }
            return response.getBody();
            
        } catch (Exception e) {
            throw new CountryNotFoundException("Error getting country information for: " + countryCode, e);
        }
    }
}
