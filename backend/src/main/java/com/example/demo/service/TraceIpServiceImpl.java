package com.example.demo.service;

import com.example.demo.model.to.CountryInfoTO;
import com.example.demo.model.to.TraceIpResponseTO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TraceIpServiceImpl implements TraceIpService {

    @Autowired
    private GeoLocationIpService geoLocationService;

    @Autowired
    private CountryDataService countryDataService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private TraceIpRecordService traceRecordService;

    @Override
    public TraceIpResponseTO getCountryInfoByIp(String ip) {
        log.info("Starting information lookup for IP: {}", ip);

        Map<String, Object> ipData = geoLocationService.getLocationInfo(ip);
        String countryCode = (String) ipData.get("country_code");

        CountryInfoTO countryInfo = countryDataService.buildCountryInfo(ipData, countryCode);

        Double exchangeRate = exchangeRateService.fetchDollarExchangeRate(countryInfo.getLocalCurrency());
        countryInfo.setDollarExchangeRate(exchangeRate);

        traceRecordService.recordTrace(countryInfo.getName(), countryInfo.getDistanceFromBuenosAiresKm());

        TraceIpResponseTO response = new TraceIpResponseTO();
        response.setIp(ip);
        response.setCountryInfo(countryInfo);

        log.info("Final response generated: {}", response);
        return response;
    }
}
