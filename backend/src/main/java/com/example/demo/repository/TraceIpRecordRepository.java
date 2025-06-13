package com.example.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.entity.TraceIpRecord;

import jakarta.transaction.Transactional;


public interface TraceIpRecordRepository extends JpaRepository<TraceIpRecord, Long> {

    @Query("SELECT MAX(r.distanceFromBuenosAiresKm) FROM TraceIpRecord r")
    Double findMaxDistance();

    @Query("SELECT MIN(r.distanceFromBuenosAiresKm) FROM TraceIpRecord r")
    Double findMinDistance();

    @Query("SELECT SUM(r.distanceFromBuenosAiresKm * r.invocations) / SUM(r.invocations) FROM TraceIpRecord r")
    Double findAverageDistance();

    Optional<TraceIpRecord> findByCountry(String country);

    @Modifying
    @Transactional
    @Query("UPDATE TraceIpRecord t SET t.invocations = t.invocations + 1 WHERE t.country = :country")
    int incrementInvocationsByCountry(String country);

}
