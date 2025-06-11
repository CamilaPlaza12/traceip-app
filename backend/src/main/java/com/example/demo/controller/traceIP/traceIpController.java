package com.example.demo.controller.traceIP;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.to.CountryInfoTO;
import com.example.demo.model.to.TraceIpResponseTO;
import com.example.demo.service.TraceIpService;


@RestController
public class TraceIpController implements TraceIpAPI {

    private final TraceIpService traceIpService;

    public TraceIpController(TraceIpService traceIpService) {
        this.traceIpService = traceIpService;
    }

    @Override
    public ResponseEntity<?> traceIP(String ip) {
        return ResponseEntity.ok("Recibí la IP: " + ip);
    }

   @Override
    public ResponseEntity<TraceIpResponseTO> getCountryInfoByIp(String ip) {
        System.out.println("STARTING PROCESS FOR IP: " + ip);
        try {
            TraceIpResponseTO to = traceIpService.getCountryInfoByIp(ip);
            return ResponseEntity.ok(to);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
