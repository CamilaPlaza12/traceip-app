package com.example.demo.service;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.IpApiClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GeoLocationIpService {
    @Autowired
    private IpApiClient ipApiClient;

    public Map<String, Object> getLocationInfo(String ip) {
        Map<String, Object> ipData = ipApiClient.getLocationInfo(ip);
        log.info("Data obtained from ipApiClient: {}", ipData);
        return ipData;
    }
    
}
