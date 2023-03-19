package com.example.hotelantique.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfController {

    @GetMapping("/aboutUs")
    public String aboutUs(){
        return "about-us";
    }

    @GetMapping("/reservations/successful")
    public String successfulReservation(){
        return "successful-reservation";
    }

    @GetMapping("/payment/card")
    public String cardPayment(){
        return "card-details";
    }

    @GetMapping("/pages/admin")
    public String adminPage(){
        return "admin-page";
    }

}
