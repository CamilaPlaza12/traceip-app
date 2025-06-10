package com.example.demo.model.to;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class CountryInfoTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "ISO code is required")
    @Size(min = 2, max = 3, message = "ISO code must be between 2 and 3 characters")
    private String isoCode;

    @NotNull(message = "Languages list cannot be null")
    @Size(min = 1, message = "At least one language is required")
    private List<@NotBlank(message = "Language cannot be empty") String> languages;

    @NotNull(message = "Current times list cannot be null")
    @Size(min = 1, message = "At least one current time is required")
    private List<@Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "Invalid time format, expected HH:mm") String> currentTimes;

    @NotNull(message = "Distance from Buenos Aires cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Distance must be greater than 0")
    private Double distanceFromBuenosAiresKm;

    @NotBlank(message = "Local currency is required")
    private String localCurrency;

    @DecimalMin(value = "0.0", inclusive = false, message = "Exchange rate must be greater than 0")
    private Double dollarExchangeRate;
}
