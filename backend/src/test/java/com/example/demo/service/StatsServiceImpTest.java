package com.example.demo.service;

import com.example.demo.repository.TraceIpRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatsServiceImpTest {

    @Mock
    private TraceIpRecordRepository repository;

    @InjectMocks
    private StatsServiceImp statsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMaxDistanceWhenRepositoryReturnsValue() {
        when(repository.findMaxDistance()).thenReturn(1000.0);

        Double result = statsService.getMaxDistance();

        assertEquals(1000.0, result);
        verify(repository).findMaxDistance();
    }

    @Test
    void getMaxDistanceReturnsZeroWhenRepositoryReturnsNull() {
        when(repository.findMaxDistance()).thenReturn(null);

        Double result = statsService.getMaxDistance();

        assertEquals(0.0, result);
        verify(repository).findMaxDistance();
    }

    @Test
    void getMaxDistanceThrowsExceptionWhenRepositoryThrows() {
        when(repository.findMaxDistance()).thenThrow(new RuntimeException("DB error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            statsService.getMaxDistance();
        });

        assertTrue(exception.getMessage().contains("Could not retrieve max distance"));
        verify(repository).findMaxDistance();
    }

    @Test
    void getMinDistanceWhenRepositoryReturnsValue() {
        when(repository.findMinDistance()).thenReturn(10.0);

        Double result = statsService.getMinDistance();

        assertEquals(10.0, result);
        verify(repository).findMinDistance();
    }

    @Test
    void getMinDistanceReturnsZeroWhenRepositoryReturnsNull() {
        when(repository.findMinDistance()).thenReturn(null);

        Double result = statsService.getMinDistance();

        assertEquals(0.0, result);
        verify(repository).findMinDistance();
    }

    @Test
    void getMinDistanceThrowsExceptionWhenRepositoryThrows() {
        when(repository.findMinDistance()).thenThrow(new RuntimeException("DB error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            statsService.getMinDistance();
        });

        assertTrue(exception.getMessage().contains("Could not retrieve min distance"));
        verify(repository).findMinDistance();
    }

    @Test
    void getAverageDistanceReturnsValueWhenRepositoryReturnsValue() {
        when(repository.findAverageDistance()).thenReturn(500.0);

        Double result = statsService.getAverageDistance();

        assertEquals(500.0, result);
        verify(repository).findAverageDistance();
    }

    @Test
    void getAverageDistanceReturnsZeroWhenRepositoryReturnsNull() {
        when(repository.findAverageDistance()).thenReturn(null);

        Double result = statsService.getAverageDistance();

        assertEquals(0.0, result);
        verify(repository).findAverageDistance();
    }

    @Test
    void getAverageDistanceThrowsExceptionWhenRepositoryThrows() {
        when(repository.findAverageDistance()).thenThrow(new RuntimeException("DB error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            statsService.getAverageDistance();
        });

        assertTrue(exception.getMessage().contains("Could not retrieve average distance"));
        verify(repository).findAverageDistance();
    }
}
