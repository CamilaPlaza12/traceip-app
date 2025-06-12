package com.example.demo.config;
import java.time.ZonedDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.entity.TraceIpRecord;
import com.example.demo.repository.TraceIpRecordRepository;

@Configuration
public class DataSeed {
    @Bean
    CommandLineRunner seedDatabase(TraceIpRecordRepository repo) {
        return args -> {
            if (repo.count() == 0) {

                repo.save(new TraceIpRecord("Brasil", 2862.0, 2L));

                repo.save(new TraceIpRecord("Uruguay", 320.0, 5L));

                repo.save(new TraceIpRecord("Estados Unidos", 9002.23, 4L));
            }
        };
    }
    
}
