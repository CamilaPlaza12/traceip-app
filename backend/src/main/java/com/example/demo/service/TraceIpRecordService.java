package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.entity.TraceIpRecord;
import com.example.demo.repository.TraceIpRecordRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TraceIpRecordService {
    @Autowired
    private TraceIpRecordRepository traceIpRecordRepository;

    public void recordTrace(String country, Double distanceKm) {
        if (country == null) {
            log.warn("Country is null, cannot record trace");
            return;
        }
        int updatedRows = traceIpRecordRepository.incrementInvocationsByCountry(country);

        if (updatedRows == 0) {
            TraceIpRecord record = new TraceIpRecord();
            record.setCountry(country);
            record.setDistanceFromBuenosAiresKm(distanceKm);
            record.setInvocations(1L);
            traceIpRecordRepository.save(record);
            log.info("New trace record created for country: {}", country);
        } else {
            log.info("Incremented invocations for country: {}", country);
        }
    }
}
