package com.example.demo.controller;

import com.example.demo.controller.traceIP.TraceIpController;
import com.example.demo.model.to.TraceIpResponseTO;
import com.example.demo.service.TraceIpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TraceIpControllerTest {

    @Mock
    private TraceIpService traceIpService;

    @InjectMocks
    private TraceIpController traceIpController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTraceIP_returnString() {
        String ip = "8.8.8.8";
        ResponseEntity<?> response = traceIpController.traceIP(ip);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Recibí la IP: " + ip, response.getBody());
    }

    @Test
    void testGetCountryInfoByIp_OK() {
        String ip = "8.8.8.8";
        TraceIpResponseTO mockResponse = new TraceIpResponseTO();
        when(traceIpService.getCountryInfoByIp(ip)).thenReturn(mockResponse);

        ResponseEntity<TraceIpResponseTO> response = traceIpController.getCountryInfoByIp(ip);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(traceIpService, times(1)).getCountryInfoByIp(ip);
    }

    @Test
    void testGetCountryInfoByIp_BadRequest() {
        String ip = "bad.request";
        when(traceIpService.getCountryInfoByIp(ip)).thenThrow(new IllegalArgumentException());

        ResponseEntity<TraceIpResponseTO> response = traceIpController.getCountryInfoByIp(ip);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetCountryInfoByIp_UnprocessableEntity() {
        String ip = "unprocessable";
        when(traceIpService.getCountryInfoByIp(ip)).thenThrow(new UnsupportedOperationException());

        ResponseEntity<TraceIpResponseTO> response = traceIpController.getCountryInfoByIp(ip);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    void testGetCountryInfoByIp_Error() {
        String ip = "error";

        when(traceIpService.getCountryInfoByIp(ip)).thenThrow(new RuntimeException());

        ResponseEntity<TraceIpResponseTO> response = traceIpController.getCountryInfoByIp(ip);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
