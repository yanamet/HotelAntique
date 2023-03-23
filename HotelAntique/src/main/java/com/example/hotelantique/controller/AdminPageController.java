package com.example.hotelantique.controller;

import com.example.hotelantique.init.DataInit;
import com.example.hotelantique.model.dtos.userDTO.UserAdminPageDTO;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/pages/admin")
public class AdminPageController {


    private final UserService userService;
    private final RestTemplate restTemplate;

    public AdminPageController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserAdminPageDTO>> getAllUsers(@AuthenticationPrincipal UserDetails userDetails){
        UserEntity admin = this.userService.getByUsername(userDetails.getUsername()).get();
        return ResponseEntity.ok(userService.getAllUsersOtherThan(admin.getId()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserAdminPageDTO> getUserWithId(@PathVariable("id") long id){
       Optional<UserAdminPageDTO> user = this.userService.getUserAdminDTOById(id);

       return user.map(ResponseEntity::ok)
               .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserAdminPageDTO> deleteUserById(@PathVariable("id") long id){
        this.userService.deleteById(id);

        return  ResponseEntity.noContent().build();
    }





}
