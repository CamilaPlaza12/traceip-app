package com.example.demo.model.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TraceIpRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private Double distanceFromBuenosAiresKm;
    private Long invocations = 0L;


    public TraceIpRecord(String country, Double distanceFromBuenosAiresKm) {
        this.country = country;
        this.distanceFromBuenosAiresKm = distanceFromBuenosAiresKm;
        this.invocations = 1L;
    }

    public TraceIpRecord() {
    }

        public TraceIpRecord(String country, Double distanceFromBuenosAiresKm, Long invocations) {
        this.country = country;
        this.distanceFromBuenosAiresKm = distanceFromBuenosAiresKm;
        this.invocations = invocations;
    }
    
}
