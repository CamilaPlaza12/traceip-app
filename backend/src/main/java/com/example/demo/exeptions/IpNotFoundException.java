package com.example.demo.exeptions;

public class IpNotFoundException extends RuntimeException {

    public IpNotFoundException(String message) {
        super(message);
    }

    public IpNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
