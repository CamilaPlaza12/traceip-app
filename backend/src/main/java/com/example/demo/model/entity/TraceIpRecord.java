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
    private Double distance;
    private ZonedDateTime creationTime;

    public TraceIpRecord(String country, Double distance, ZonedDateTime creationTime) {
        this.country = country;
        this.distance = distance;
        this.creationTime = creationTime;
    }

    public TraceIpRecord() {
    }
    
}
