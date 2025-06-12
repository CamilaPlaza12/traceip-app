package com.example.demo.service;
import java.time.ZonedDateTime;

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
        TraceIpRecord record = new TraceIpRecord();
        record.setCountry(country);
        record.setDistance(distanceKm);
        record.setCreationTime(ZonedDateTime.now());
        traceIpRecordRepository.save(record);
        log.info("Trace record saved for country: {}", country);
    }
}
