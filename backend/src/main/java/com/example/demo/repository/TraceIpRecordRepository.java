package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.entity.TraceIpRecord;


public interface TraceIpRecordRepository extends JpaRepository<TraceIpRecord, Long> {

}
