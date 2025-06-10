package com.example.demo.controller.traceIP;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class traceIpController implements traceIpAPI {

    @Override
    public ResponseEntity<?> traceIP(String ip) {
        return ResponseEntity.ok("Recibí la IP: " + ip);
    }
}
