package com.example.demo.model.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TraceIpResponseTO {

    @NotBlank(message = "IP is required")
    @Pattern(
        regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$",
        message = "Invalid IP format"
    )
    private String ip;

    @NotNull(message = "Country info is required")
    private CountryInfoTO countryInfo;
}
