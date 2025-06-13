package com.example.demo.exeptions;

public class CurrencyApiException extends RuntimeException {

    public CurrencyApiException(String message) {
        super(message);
    }

    public CurrencyApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
