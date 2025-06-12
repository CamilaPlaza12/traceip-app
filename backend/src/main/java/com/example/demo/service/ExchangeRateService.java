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
        if (currencyCode == null) {
            log.warn("Currency not specified. Exchange rate cannot be calculated");
            return null;
        }
        Map<String, Object> exchangeData = fixerApiClient.getLatestExchangeRates();
        Map<String, Double> rates = (Map<String, Double>) exchangeData.get("rates");

        if (rates == null || !rates.containsKey(currencyCode) || !rates.containsKey("USD")) {
            log.warn("Exchange rate data incomplete for currency: {}", currencyCode);
            return null;
        }

        Double exchangeRate = 1 / (rates.get(currencyCode) / rates.get("USD"));
        log.info("Exchange rate from {} to USD: {}", currencyCode, exchangeRate);
        return exchangeRate;
    }
}
