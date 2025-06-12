package com.example.demo.service;

import com.example.demo.model.to.CountryInfoTO;
import com.example.demo.model.to.TraceIpResponseTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraceIpServiceImplTest {

    @InjectMocks
    private TraceIpServiceImpl traceIpService;

    @Mock
    private GeoLocationIpService geoLocationService;

    @Mock
    private CountryDataService countryDataService;

    @Mock
    private ExchangeRateService exchangeRateService;

    @Mock
    private TraceIpRecordService traceIpRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCountryInfoWhenValidIpIsProvided() {
        String ip = "123.45.67.89";
        String countryCode = "AR";

        Map<String, Object> mockIpData = new HashMap<>();
        mockIpData.put("country_code", countryCode);

        CountryInfoTO mockCountryInfo = new CountryInfoTO();
        mockCountryInfo.setName("Argentina");
        mockCountryInfo.setLocalCurrency("ARS");
        mockCountryInfo.setDistanceFromBuenosAiresKm(0.0);

        when(geoLocationService.getLocationInfo(ip)).thenReturn(mockIpData);
        when(countryDataService.buildCountryInfo(mockIpData, countryCode)).thenReturn(mockCountryInfo);
        when(exchangeRateService.fetchDollarExchangeRate("ARS")).thenReturn(100.0);


        TraceIpResponseTO response = traceIpService.getCountryInfoByIp(ip);

        assertNotNull(response);
        assertEquals(ip, response.getIp());
        assertNotNull(response.getCountryInfo());
        assertEquals("Argentina", response.getCountryInfo().getName());
        assertEquals("ARS", response.getCountryInfo().getLocalCurrency());
        assertEquals(100.0, response.getCountryInfo().getDollarExchangeRate());

        verify(geoLocationService).getLocationInfo(ip);
        verify(countryDataService).buildCountryInfo(mockIpData, countryCode);
        verify(exchangeRateService).fetchDollarExchangeRate("ARS");
        verify(traceIpRecordService).recordTrace("Argentina", 0.0);
    }

    @Test
    void shouldHandleNullCurrencySuccessfully() {
        String ip = "200.100.50.25";
        String countryCode = "UY";

        Map<String, Object> mockIpData = new HashMap<>();
        mockIpData.put("country_code", countryCode);

        CountryInfoTO mockCountryInfo = new CountryInfoTO();
        mockCountryInfo.setName("Uruguay");
        mockCountryInfo.setLocalCurrency(null);
        mockCountryInfo.setDistanceFromBuenosAiresKm(500.0);

        when(geoLocationService.getLocationInfo(ip)).thenReturn(mockIpData);
        when(countryDataService.buildCountryInfo(mockIpData, countryCode)).thenReturn(mockCountryInfo);
        when(exchangeRateService.fetchDollarExchangeRate(null)).thenReturn(null);

        TraceIpResponseTO response = traceIpService.getCountryInfoByIp(ip);

        assertNotNull(response);
        assertEquals(ip, response.getIp());
        assertEquals("Uruguay", response.getCountryInfo().getName());
        assertNull(response.getCountryInfo().getDollarExchangeRate());

        verify(traceIpRecordService).recordTrace("Uruguay", 500.0);
    }
}
