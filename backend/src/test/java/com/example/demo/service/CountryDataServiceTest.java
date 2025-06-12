package com.example.demo.service;

import com.example.demo.client.CountriesClient;
import com.example.demo.exeptions.CountryNotFoundException;
import com.example.demo.model.to.CountryInfoTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryDataServiceTest {

    private CountryDataService countryDataService;
    private CountriesClient countriesClient;

    @BeforeEach
    void setUp() {
        countriesClient = mock(CountriesClient.class);
        countryDataService = new CountryDataService();
        countryDataService.countriesClient = countriesClient;
    }

    @Test
    void shouldBuildCountryInfoSuccessfully() {
        String countryCode = "AR";
        Map<String, Object> ipData = Map.of(
                "country_name", "Argentina"
        );

        Map<String, Object> countryData = new HashMap<>();
        countryData.put("latlng", List.of(-34.6037, -58.3816));
        countryData.put("currencies", List.of(Map.of("code", "ARS")));
        countryData.put("languages", List.of(Map.of("name", "Spanish")));
        countryData.put("timezones", List.of("America/Argentina/Buenos_Aires"));

        when(countriesClient.getCountryInfo(countryCode)).thenReturn(countryData);
        CountryInfoTO result = countryDataService.buildCountryInfo(ipData, countryCode);


        assertEquals("Argentina", result.getName());
        assertEquals("AR", result.getIsoCode());
        assertEquals("ARS", result.getLocalCurrency());
        assertEquals(List.of("Spanish"), result.getLanguages());
        assertNotNull(result.getCurrentTimes());
        assertEquals(0.0, result.getDistanceFromBuenosAiresKm());
    }

    @Test
    void shouldReturnNullCurrencyIfNotPresent() {

        String countryCode = "ZZ";
        Map<String, Object> ipData = Map.of("country_name", "Testlandia");

        Map<String, Object> countryData = new HashMap<>();
        countryData.put("latlng", List.of(0.0, 0.0));
        countryData.put("currencies", new ArrayList<>());
        countryData.put("languages", List.of(Map.of("name", "Esperanto")));
        countryData.put("timezones", List.of("UTC"));

        when(countriesClient.getCountryInfo(countryCode)).thenReturn(countryData);
        CountryInfoTO result = countryDataService.buildCountryInfo(ipData, countryCode);

        assertNull(result.getLocalCurrency());
        assertEquals(List.of("Esperanto"), result.getLanguages());
    }

    @Test
    void shouldReturnNullDistanceIfLatLngIsNull() {
        String countryCode = "XX";
        Map<String, Object> ipData = Map.of("country_name", "ARG");

        Map<String, Object> countryData = new HashMap<>();
        countryData.put("currencies", List.of(Map.of("code", "$$$")));
        countryData.put("languages", List.of(Map.of("name", "SPA")));
        countryData.put("timezones", List.of("UTC"));

        when(countriesClient.getCountryInfo(countryCode)).thenReturn(countryData);
        CountryInfoTO result = countryDataService.buildCountryInfo(ipData, countryCode);

        assertNull(result.getDistanceFromBuenosAiresKm());
    }

    @Test
    void shouldHandleEmptyLanguagesAndTimezones() {
        String countryCode = "YY";
        Map<String, Object> ipData = Map.of("country_name", "ARG");

        Map<String, Object> countryData = new HashMap<>();
        countryData.put("latlng", List.of(10.0, 10.0));
        countryData.put("currencies", List.of(Map.of("code", "YYY")));

        when(countriesClient.getCountryInfo(countryCode)).thenReturn(countryData);
        CountryInfoTO result = countryDataService.buildCountryInfo(ipData, countryCode);

        assertTrue(result.getLanguages().isEmpty());
        assertTrue(result.getCurrentTimes().isEmpty());
    }

    @Test
    void shouldThrowCountryNotFoundExceptionWhenClientFails() {
        String invalidCode = "XYZ";
        Map<String, Object> ipData = Map.of("country_name", "ARG");

        when(countriesClient.getCountryInfo(invalidCode))
                .thenThrow(new CountryNotFoundException("Error getting country information for: XYZ"));
        CountryNotFoundException exception = assertThrows(CountryNotFoundException.class, () -> {
            countryDataService.buildCountryInfo(ipData, invalidCode);
        });

        assertTrue(exception.getMessage().contains("XYZ"));
    }

}
