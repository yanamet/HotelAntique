package com.example.hotelantique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HotelAntiqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelAntiqueApplication.class, args);
    }

}
