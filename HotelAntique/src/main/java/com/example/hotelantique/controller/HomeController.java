package com.example.hotelantique.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/reservations/add")
    public String addReserve(){
        return "reservation-add";
    }

}
