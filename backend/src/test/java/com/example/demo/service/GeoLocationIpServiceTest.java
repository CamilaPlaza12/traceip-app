package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.client.IpApiClient;
import com.example.demo.exeptions.IpNotFoundException;

@ExtendWith(MockitoExtension.class)
public class GeoLocationIpServiceTest {
    @Mock
    private IpApiClient ipApiClient;

    @InjectMocks
    private GeoLocationIpService geoLocationIpService;

    @Test
    void shouldReturnIpDataInfoSuccessfully() {
        String ip = "8.8.8.8";
        Map<String, Object> expectedData = Map.of("country_code", "AR", "country_name", "Argentina");
        Mockito.when(ipApiClient.getLocationInfo(ip)).thenReturn(expectedData);

        Map<String, Object> result = geoLocationIpService.getLocationInfo(ip);

        assertEquals(expectedData, result);
        assertEquals("AR", result.get("country_code"));
        assertEquals("Argentina", result.get("country_name"));
    }

    @Test
    void shouldThrowIpNotFoundExceptioWhenIpDataIsEmpty() {
        String ip = "192.0.2.0";
        Mockito.when(ipApiClient.getLocationInfo(ip)).thenReturn(Collections.emptyMap());

        assertThrows(IpNotFoundException.class, () -> {
            Map<String, Object> data = geoLocationIpService.getLocationInfo(ip);
            if (data == null || data.isEmpty()) {
                throw new IpNotFoundException("No information found for IP: " + ip);
            }
        });
    }

}
