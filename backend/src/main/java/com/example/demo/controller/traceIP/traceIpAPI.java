package com.example.demo.controller.traceIP;

import com.example.demo.model.to.TraceIpResponseTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/trace-ip")
public interface TraceIpAPI {

    @GetMapping("/api/trace-test")
    ResponseEntity<?> traceIP(@RequestParam String ip);

    @Operation(summary = "Get country info from IP address", description = "Returns information about the country of the given IP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Country information successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid IP address format"),
            @ApiResponse(responseCode = "404", description = "Country not found for given IP"),
            @ApiResponse(responseCode = "422", description = "IP address is valid but can't be processed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/country-info")
    ResponseEntity<TraceIpResponseTO> getCountryInfoByIp(
            @Parameter(description = "IP address (e.g., 192.168.0.1)", required = true)
            @RequestParam
            @NotBlank(message = "IP address is required")
            @Pattern(
                regexp = "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.|$)){4}$",
                message = "Invalid IP address format"
             ) 
             String ip
    );  
}
