package com.example.demo.service;

import com.example.demo.client.FixerApiClient;
import com.example.demo.exeptions.CurrencyApiException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExchangeRateServiceTest {

    @Mock
    private FixerApiClient fixerApiClient;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnExchangeRateWhenCurrencyIsValid() {
        String currencyCode = "ARS";
        Map<String, Object> mockedData = Map.of(
            "rates", Map.of(
                "ARS", 900.0,
                "USD", 1.0
            )
        );

        when(fixerApiClient.getLatestExchangeRates()).thenReturn(mockedData);
        Double result = exchangeRateService.fetchDollarExchangeRate(currencyCode);

        assertNotNull(result);
        assertEquals(1 / (900.0 / 1.0), result);
    }

    @Test
    void shouldReturnNullWhenCurrencyCodeIsNull() {
        Double result = exchangeRateService.fetchDollarExchangeRate(null);
        assertNull(result);
    }

    @Test
    void shouldReturnNullWhenRatesAreMissing() {
        when(fixerApiClient.getLatestExchangeRates()).thenReturn(Map.of());
        Double result = exchangeRateService.fetchDollarExchangeRate("ARS");
        assertNull(result);
    }

    @Test
    void shouldReturnNullWhenCurrencyNotPresentInRates() {
        Map<String, Object> mockedData = Map.of(
            "rates", Map.of("USD", 1.0)
        );
        when(fixerApiClient.getLatestExchangeRates()).thenReturn(mockedData);
        Double result = exchangeRateService.fetchDollarExchangeRate("ARS");
        assertNull(result);
    }

    @Test
    void shouldThrowCurrencyApiExceptionWhenFixerFails() {
        when(fixerApiClient.getLatestExchangeRates())
                .thenThrow(new CurrencyApiException("Fixer API exploded"));

        assertThrows(CurrencyApiException.class, () -> {
            exchangeRateService.fetchDollarExchangeRate("ARS");
        });
    }
}
