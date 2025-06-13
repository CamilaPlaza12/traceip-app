package com.example.demo.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.CountriesClient;
import com.example.demo.model.to.CountryInfoTO;
import com.example.demo.util.DistanceCalculator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountryDataService {
    @Autowired CountriesClient countriesClient;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");


    public CountryInfoTO buildCountryInfo(Map<String, Object> ipData, String countryCode) {
        Map<String, Object> countryData = countriesClient.getCountryInfo(countryCode);

        List<Double> latlng = (List<Double>) countryData.get("latlng");
        Double lat = latlng != null && latlng.size() == 2 ? latlng.get(0) : null;
        Double lon = latlng != null && latlng.size() == 2 ? latlng.get(1) : null;

        String currencyCode = extractCurrencyCodeFromCountry(countryData);
        List<String> languages = extractLanguages(countryData);
        List<String> timezones = extractTimezones(countryData);
        List<String> currentTimes = getCurrentTimesFromTimezones(timezones);
        Double distance = calculateDistanceFromBuenosAires(lat, lon);

        CountryInfoTO countryInfo = new CountryInfoTO();
        countryInfo.setName((String) ipData.get("country_name"));
        countryInfo.setIsoCode(countryCode);
        countryInfo.setLanguages(languages);
        countryInfo.setCurrentTimes(currentTimes);
        countryInfo.setDistanceFromBuenosAiresKm(distance);
        countryInfo.setLocalCurrency(currencyCode);

        return countryInfo;
    }

    private String extractCurrencyCodeFromCountry(Map<String, Object> countryData) {
        List<Map<String, String>> currencies = (List<Map<String, String>>) countryData.get("currencies");
        if (currencies != null && !currencies.isEmpty()) {
            return currencies.get(0).get("code");
        }
        log.warn("No currency information found in countryData: {}", countryData);
        return null;
    }

    private List<String> extractLanguages(Map<String, Object> countryData) {
        List<Map<String, String>> languagesList = (List<Map<String, String>>) countryData.get("languages");
        List<String> languageNames = new ArrayList<>();
        if (languagesList != null) {
            for (Map<String, String> lang : languagesList) {
                languageNames.add(lang.get("name"));
            }
        }
        return languageNames;
    }

    private List<String> extractTimezones(Map<String, Object> countryData) {
        return (List<String>) countryData.getOrDefault("timezones", Collections.emptyList());
    }

    private List<String> getCurrentTimesFromTimezones(List<String> timezones) {
        List<String> currentTimes = new ArrayList<>();
        for (String tz : timezones) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(tz));
            String formattedTime = zonedDateTime.format(TIME_FORMATTER);

            // Extraemos el nombre de la zona (ejemplo: "UTC", "UTC+01:00")
            String zone = zonedDateTime.getZone().toString();

            currentTimes.add(formattedTime + " (" + zone + ")");
        }
        return currentTimes;
    }

    private Double calculateDistanceFromBuenosAires(Double lat, Double lon) {
        if (lat == null || lon == null) {
            log.warn("Distance could not be calculated: null coordinates");
            return null;
        }
        double distance = DistanceCalculator.calculateDistanceInKm(lat, lon, -34.6037, -58.3816);
        return Math.round(distance * 100.0) / 100.0;
    }
    
}
