package com.example.demo.model.to;

import lombok.Data;
import java.util.List;

@Data
public class CountryInfoTO {
    private String name;
    private String isoCode;
    private List<String> languages;
    private List<String> currentTimes;
    private Double distanceFromBuenosAiresKm;
    private String localCurrency;
    private Double dollarExchangeRate;
}
