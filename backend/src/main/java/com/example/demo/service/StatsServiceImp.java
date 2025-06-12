package com.example.demo.service;

import com.example.demo.repository.TraceIpRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatsServiceImp implements StatsService {

    @Autowired
    private TraceIpRecordRepository repository;

    @Override
    public Double getMaxDistance() {
        try {
            Double max = repository.findMaxDistance();
            if (max == null) {
                log.warn("No data found for max distance.");
                return 0.0;
            }
            return max;
        } catch (Exception e) {
            log.error("Error retrieving max distance", e);
            throw new RuntimeException("Could not retrieve max distance", e);
        }
    }

    @Override
    public Double getMinDistance() {
        try {
            Double min = repository.findMinDistance();
            if (min == null) {
                log.warn("No data found for min distance.");
                return 0.0;
            }
            return min;
        } catch (Exception e) {
            log.error("Error retrieving min distance", e);
            throw new RuntimeException("Could not retrieve min distance", e);
        }
    }

    @Override
    public Double getAverageDistance() {
        try {
            Double avg = repository.findAverageDistance();
            if (avg == null) {
                log.warn("No data found for average distance.");
                return 0.0;
            }
            return avg;
        } catch (Exception e) {
            log.error("Error retrieving average distance", e);
            throw new RuntimeException("Could not retrieve average distance", e);
        }
    }
}
