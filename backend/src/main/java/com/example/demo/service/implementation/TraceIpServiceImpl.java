package com.example.demo.service.implementation;

import com.example.demo.client.FixerApiClient;
import com.example.demo.client.IpApiClient;
import com.example.demo.exeptions.IpNotFoundException;
import com.example.demo.client.CountriesClient;
import com.example.demo.model.to.CountryInfoTO;
import com.example.demo.model.to.TraceIpResponseTO;
import com.example.demo.service.TraceIpService;
import com.example.demo.util.DistanceCalculator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Slf4j
public class TraceIpServiceImpl implements TraceIpService {

    @Autowired
    private IpApiClient ipApiClient;

    @Autowired
    private CountriesClient countriesClient;

    @Autowired
    private FixerApiClient fixerApiClient;


    @Override
    public TraceIpResponseTO getCountryInfoByIp(String ip) {
        log.info("Starting information lookup for IP: {}", ip);

        Map<String, Object> ipData = getIpData(ip);
        String countryCode = extractCountryCode(ipData);

        CountryInfoTO countryInfo = buildCountryInfo(ipData, countryCode);

        TraceIpResponseTO response = new TraceIpResponseTO();
        response.setIp(ip);
        response.setCountryInfo(countryInfo);

        log.info("Final response generated: {}", response);
        return response;
    }

    private Map<String, Object> getIpData(String ip) {
        Map<String, Object> ipData = ipApiClient.getLocationInfo(ip);
        if (ipData == null || ipData.isEmpty()) {
            throw new IpNotFoundException("No information found for IP: " + ip);
        }
        log.info("Data obtained from ipApiClient: {}", ipData);
        return ipData;
    }


    private String extractCountryCode(Map<String, Object> ipData) {
        return (String) ipData.get("country_code");
    }

    private String extractCurrencyCodeFromCountry(Map<String, Object> countryData) {
        List<Map<String, String>> currencies = (List<Map<String, String>>) countryData.get("currencies");
        if (currencies != null && !currencies.isEmpty()) {
            return currencies.get(0).get("code");
        }
        log.warn("No currency information found in countryData: {}", countryData);
        return null;
    }

    private CountryInfoTO buildCountryInfo(Map<String, Object> ipData, String countryCode) {
        Map<String, Object> countryData;
        countryData = countriesClient.getCountryInfo(countryCode);
        List<Double> latlng = (List<Double>) countryData.get("latlng");
        Double lat = latlng.get(0);
        Double lon = latlng.get(1);

        String currencyCode = extractCurrencyCodeFromCountry(countryData);
        List<String> languages = extractLanguages(countryData);
        List<String> timezones = extractTimezones(countryData);
        List<String> currentTimes = getCurrentTimesFromTimezones(timezones);
        Double distance = calculateDistanceFromBuenosAires(lat, lon);
        Double exchangeRate = fetchDollarExchangeRate(currencyCode);

        CountryInfoTO countryInfo = new CountryInfoTO();
        countryInfo.setName((String) ipData.get("country_name"));
        countryInfo.setIsoCode(countryCode);
        countryInfo.setLanguages(languages);
        countryInfo.setCurrentTimes(currentTimes);
        countryInfo.setDistanceFromBuenosAiresKm(distance);
        countryInfo.setLocalCurrency(currencyCode);
        countryInfo.setDollarExchangeRate(exchangeRate);

        return countryInfo;
    }

    private List<String> extractLanguages(Map<String, Object> countryData) {
        List<Map<String, String>> languagesList = (List<Map<String, String>>) countryData.get("languages");
        List<String> languageNames = new ArrayList<>();
        for (Map<String, String> lang : languagesList) {
            languageNames.add(lang.get("name"));
        }
        return languageNames;
    }

    private List<String> extractTimezones(Map<String, Object> countryData) {
        return (List<String>) countryData.get("timezones");
    }


    private List<String> getCurrentTimesFromTimezones(List<String> timezones) {
        List<String> currentTimes = new ArrayList<>();
        for (String tz : timezones) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(tz));
            currentTimes.add(zonedDateTime.toString());
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

    private Double fetchDollarExchangeRate(String currencyCode) {
        if (currencyCode == null) {
            log.warn("Currency not specified. Exchange rate cannot be calculated");
            return null;
        }
        Map<String, Object> exchangeData = fixerApiClient.getLatestExchangeRates();
        Map<String, Double> rates = (Map<String, Double>) exchangeData.get("rates");
            
        Double exchangeRate = 1 / (rates.get(currencyCode) / rates.get("USD"));
        log.info("Exchange rate from {} to USD: {}", currencyCode, exchangeRate);
        return exchangeRate;
    }
}
