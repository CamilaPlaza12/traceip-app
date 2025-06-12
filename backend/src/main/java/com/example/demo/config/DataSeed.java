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
                double distanciaBrasil = 2200.0;
                double distanciaUruguay = 320.0;
                double distanciaEEUU = 9002.23;

                repo.save(new TraceIpRecord("Brasil", distanciaBrasil, ZonedDateTime.now()));
                repo.save(new TraceIpRecord("Brasil", distanciaBrasil, ZonedDateTime.now()));
                repo.save(new TraceIpRecord("Brasil", distanciaBrasil, ZonedDateTime.now()));

                repo.save(new TraceIpRecord("Uruguay", distanciaUruguay, ZonedDateTime.now()));
                repo.save(new TraceIpRecord("Uruguay", distanciaUruguay, ZonedDateTime.now()));

                repo.save(new TraceIpRecord("Estados Unidos", distanciaEEUU, ZonedDateTime.now()));
                repo.save(new TraceIpRecord("Estados Unidos", distanciaEEUU, ZonedDateTime.now()));
                repo.save(new TraceIpRecord("Estados Unidos", distanciaEEUU, ZonedDateTime.now()));
                repo.save(new TraceIpRecord("Estados Unidos", distanciaEEUU, ZonedDateTime.now()));
                repo.save(new TraceIpRecord("Estados Unidos", distanciaEEUU, ZonedDateTime.now()));
            }
        };
    }
    
}
