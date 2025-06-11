package com.example.demo.service;
import com.example.demo.model.to.TraceIpResponseTO;

public interface TraceIpService {
    TraceIpResponseTO getCountryInfoByIp(String ip);
}
