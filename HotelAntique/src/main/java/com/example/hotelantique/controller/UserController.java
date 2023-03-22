package com.example.hotelantique.controller;

import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/profile")
    public String myProfile(@AuthenticationPrincipal UserDetails userDetails,
                            Model model){

        String username = userDetails.getUsername();

        UserEntity user = this.userService.getByUsername(username).get();

        model.addAttribute("user", user);

        return "my-profile";
    }

}
