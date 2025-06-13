package com.example.demo.controller.stats;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/stats")
public interface StatsApi {

    @Operation(summary = "Get the maximum distance from Buenos Aires",
               description = "Returns the greatest distance recorded among all IP trace requests")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Max distance retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/max-distance")
    ResponseEntity<Double> getMaxDistance();


    @Operation(summary = "Get the minimum distance from Buenos Aires",
               description = "Returns the shortest distance recorded among all IP trace requests")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Min distance retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/min-distance")
    ResponseEntity<Double> getMinDistance();


    @Operation(summary = "Get the average distance from Buenos Aires",
               description = "Returns the average distance calculated from all IP trace requests")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Average distance retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/average-distance")
    ResponseEntity<Double> getAverageDistance();
}
