package com.example.demo.config;

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

                repo.save(new TraceIpRecord("Brasil", 2757.32, 2L));

                repo.save(new TraceIpRecord("Mexico", 7887.76, 5L));

                repo.save(new TraceIpRecord("Estados Unidos", 9002.23, 4L));
            }
        };
    }
    
}
