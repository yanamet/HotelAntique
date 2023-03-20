package com.example.hotelantique.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/")
    public String index(Model model, Principal principal){
//        String name = "";
//        if (principal.getName() == null) {
//            name = "Anony";
//        }else{
//            name = principal.getName();
//        }
//        model.addAttribute("principal", name);
        return "index";
    }

    @GetMapping("/reservations/add")
    public String addReserve(){
        return "reservation-add";
    }

}
