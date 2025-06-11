package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class TraceipBackendApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(TraceipBackendApplication.class, args);
	}

	@Override
    public void run(String... args) {
        System.out.println("Running in: http://localhost:8080");
    }

}
