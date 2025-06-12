package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtil {

    public static Map<String, Object> getFakeIpData() {
        Map<String, Object> ipData = new HashMap<>();
        ipData.put("country_code", "US");
        ipData.put("country_name", "United States");
        ipData.put("latitude", 38.0);
        ipData.put("longitude", -97.0);
        return ipData;
    }

    public static Map<String, Object> getFakeCountryData() {
        Map<String, Object> countryData = new HashMap<>();
        countryData.put("latlng", List.of(40.0, -74.0));
        countryData.put("currencies", List.of(Map.of("code", "USD")));
        countryData.put("languages", List.of(Map.of("name", "English")));
        countryData.put("timezones", List.of("America/New_York"));

        return countryData;
    }

    public static Map<String, Object> getFakeExchangeRates() {
        Map<String, Object> exchange = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("EUR", 0.9);
        exchange.put("rates", rates);
        return exchange;
    }
    
}
