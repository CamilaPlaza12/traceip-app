package com.example.demo.controller.stats;

import com.example.demo.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StatsController implements StatsApi {

    @Autowired
    private StatsService statsService;

    @Override
    public ResponseEntity<Double> getMaxDistance() {
        try {
            Double maxDistance = statsService.getMaxDistance();
            if (maxDistance == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(maxDistance);
        } catch (Exception e) {
            log.error("Error retrieving max distance", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Double> getMinDistance() {
        try {
            Double minDistance = statsService.getMinDistance();
            if (minDistance == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(minDistance);
        } catch (Exception e) {
            log.error("Error retrieving min distance", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Double> getAverageDistance() {
        try {
            Double averageDistance = statsService.getAverageDistance();
            if (averageDistance == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(averageDistance);
        } catch (Exception e) {
            log.error("Error retrieving average distance", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
