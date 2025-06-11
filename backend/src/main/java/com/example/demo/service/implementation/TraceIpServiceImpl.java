package com.example.demo.service.implementation;

import com.example.demo.model.to.CountryInfoTO;
import com.example.demo.model.to.TraceIpResponseTO;
import com.example.demo.service.TraceIpService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TraceIpServiceImpl implements TraceIpService {

    @Override
    public TraceIpResponseTO getCountryInfoByIp(String ip) {
        // Ejemplo mockeado por ahora:
        CountryInfoTO info = new CountryInfoTO();
        info.setName("Argentina");
        info.setIsoCode("AR");
        info.setLanguages(List.of("Español"));
        info.setCurrentTimes(List.of("15:00"));
        info.setDistanceFromBuenosAiresKm(0.0);
        info.setLocalCurrency("ARS");
        info.setDollarExchangeRate(1450.00);

        TraceIpResponseTO to = new TraceIpResponseTO();
        to.setCountryInfo(info);
        to.setIp(ip);
        
        return to;
    }
}
