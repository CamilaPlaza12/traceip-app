package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.FixerApiClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExchangeRateService {

    @Autowired
    private FixerApiClient fixerApiClient;

    public Double fetchDollarExchangeRate(String currencyCode) {
        if (currencyCode == null || currencyCode.isEmpty()) {
            log.warn("Currency not specified. Exchange rate cannot be calculated");
            return null;
        }

        try {
            Map<String, Object> exchangeData = fixerApiClient.getLatestExchangeRates();

            Map<String, Object> rates = (Map<String, Object>) exchangeData.get("rates");

            if (rates == null || !rates.containsKey(currencyCode) || !rates.containsKey("USD")) {
                log.warn("Exchange rate data incomplete for currency: {}", currencyCode);
                return null;
            }

            Double currencyRate = convertToDouble(rates.get(currencyCode));
            Double usdRate = convertToDouble(rates.get("USD"));

            if (currencyRate == null || usdRate == null || usdRate == 0.0) {
                log.warn("Invalid exchange rates. currencyRate={}, usdRate={}", currencyRate, usdRate);
                return null;
            }

            Double exchangeRate = usdRate / currencyRate;

            log.info("Exchange rate from {} to USD: {}", currencyCode, exchangeRate);
            return exchangeRate;

        } catch (ClassCastException e) {
            log.error("Error casting exchange rate values: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error fetching exchange rates: {}", e.getMessage(), e);
        }

        return null;
    }

    private Double convertToDouble(Object value) {
        try {
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            } else {
                log.warn("Unexpected type for exchange rate value: {}", value);
            }
        } catch (Exception e) {
            log.error("Error converting value to Double: {}", e.getMessage(), e);
        }
        return null;
    }
}
