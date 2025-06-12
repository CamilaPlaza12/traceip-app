package com.example.demo.service;

import com.example.demo.model.entity.TraceIpRecord;
import com.example.demo.repository.TraceIpRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class TraceIpRecordServiceTest {

    @Mock
    private TraceIpRecordRepository traceIpRecordRepository;

    @InjectMocks
    private TraceIpRecordService traceIpRecordService;

    @Captor
    ArgumentCaptor<TraceIpRecord> recordCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveRecordWhenCountryIsProvided() {
        String country = "Argentina";
        Double distance = 1234.56;
        traceIpRecordService.recordTrace(country, distance);
        verify(traceIpRecordRepository, times(1)).save(recordCaptor.capture());
        TraceIpRecord savedRecord = recordCaptor.getValue();

        assert savedRecord.getCountry().equals(country);
        assert savedRecord.getDistance().equals(distance);
        assert savedRecord.getCreationTime() != null;
    }

    @Test
    void shouldNotSaveRecordWhenCountryIsNull() {
        traceIpRecordService.recordTrace(null, 100.0);
        verify(traceIpRecordRepository, never()).save(any());
    }
}
