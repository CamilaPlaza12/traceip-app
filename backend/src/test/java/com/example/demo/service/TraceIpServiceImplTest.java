package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.TestUtil;
import com.example.demo.client.CountriesClient;
import com.example.demo.client.FixerApiClient;
import com.example.demo.client.IpApiClient;
import com.example.demo.exeptions.CountryNotFoundException;
import com.example.demo.exeptions.CurrencyApiException;
import com.example.demo.exeptions.IpNotFoundException;
import com.example.demo.model.to.TraceIpResponseTO;
import com.example.demo.service.implementation.TraceIpServiceImpl;

public class TraceIpServiceImplTest {

    private Map<String, Object> ipDataUtil;
    private Map<String, Object> countryDataUtil;
    private Map<String, Object> exchangeRatesUtil;
    private String testIp;

    @Mock
    private IpApiClient ipApiClient;

    @Mock
    private CountriesClient countriesClient;

    @Mock
    private FixerApiClient fixerApiClient;

    @InjectMocks
    private TraceIpServiceImpl traceIpService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.ipDataUtil = TestUtil.getFakeIpData();
        this.countryDataUtil = TestUtil.getFakeCountryData();
        this.exchangeRatesUtil = TestUtil.getFakeExchangeRates();
        this.testIp = "8.8.8.8";
    }

    @Test
    public void testGetCountryInfoByIp_OK() {
        when(ipApiClient.getLocationInfo(testIp)).thenReturn(ipDataUtil);
        when(countriesClient.getCountryInfo("US")).thenReturn(countryDataUtil);
        when(fixerApiClient.getLatestExchangeRates()).thenReturn(exchangeRatesUtil);

        TraceIpResponseTO response = traceIpService.getCountryInfoByIp(testIp);

        assertNotNull(response);
        assertEquals("8.8.8.8", response.getIp());
        assertNotNull(response.getCountryInfo());
        assertEquals("United States", response.getCountryInfo().getName());
        assertEquals("USD", response.getCountryInfo().getLocalCurrency());
        assertEquals("US", response.getCountryInfo().getIsoCode());
        assertEquals(1, response.getCountryInfo().getLanguages().size());
        assertTrue(response.getCountryInfo().getDistanceFromBuenosAiresKm() > 0);
        assertNotNull(response.getCountryInfo().getDollarExchangeRate());
    }

    @Test
    public void testGetCountryInfoByIp_noCurrencyData() {
        
        Map<String, Object> incompleteCountryData = TestUtil.getFakeCountryData();
        incompleteCountryData.remove("currencies");

        when(ipApiClient.getLocationInfo(testIp)).thenReturn(ipDataUtil);
        when(countriesClient.getCountryInfo("US")).thenReturn(incompleteCountryData);
        when(fixerApiClient.getLatestExchangeRates()).thenReturn(exchangeRatesUtil);

        TraceIpResponseTO response = traceIpService.getCountryInfoByIp(testIp);
        assertNotNull(response);
        assertEquals("8.8.8.8", response.getIp());
        assertNotNull(response.getCountryInfo());
        assertEquals("United States", response.getCountryInfo().getName());

        assertTrue(response.getCountryInfo().getLocalCurrency() == null 
                || response.getCountryInfo().getLocalCurrency().isEmpty());
    }
    
    @Test
    public void testGetCountryInfoByIp_shouldThrowIpNotFoundException() {
        when(ipApiClient.getLocationInfo(testIp)).thenThrow(new IpNotFoundException("Simulated IP error"));
        assertThrows(IpNotFoundException.class, () -> {
            traceIpService.getCountryInfoByIp(testIp);
        });
    }

    @Test
    public void testGetCountryInfoByIp_shouldThrowCountryNotFoundException() {
        when(ipApiClient.getLocationInfo(testIp)).thenReturn(ipDataUtil);
        when(countriesClient.getCountryInfo("US")).thenThrow(new CountryNotFoundException("Simulated Country error"));

        assertThrows(CountryNotFoundException.class, () -> {
            traceIpService.getCountryInfoByIp(testIp);
        });
    }

    @Test
    public void testGetCountryInfoByIp_shouldThrowCurrencyApiException() {
        when(ipApiClient.getLocationInfo(testIp)).thenReturn(ipDataUtil);
        when(countriesClient.getCountryInfo("US")).thenReturn(countryDataUtil);
        when(fixerApiClient.getLatestExchangeRates()).thenThrow(new CurrencyApiException("Simulated Fixer error"));

        assertThrows(CurrencyApiException.class, () -> {
            traceIpService.getCountryInfoByIp(testIp);
        });
    }

}
