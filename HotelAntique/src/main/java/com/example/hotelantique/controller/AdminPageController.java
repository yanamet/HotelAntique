package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationViewDTO;
import com.example.hotelantique.model.dtos.userDTO.UserAdminPageDTO;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.service.ReservationService;
import com.example.hotelantique.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/pages/admin")
public class AdminPageController {


    private final UserService userService;
    private final ReservationService reservationService;

    public AdminPageController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }



    @GetMapping("/users")
    public ResponseEntity<List<UserAdminPageDTO>> getAllUsers(@AuthenticationPrincipal UserDetails userDetails){
        UserEntity admin = this.userService.getByUsername(userDetails.getUsername()).get();
        return ResponseEntity.ok(userService.getAllUsersOtherThan(admin.getId()));
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationViewDTO>> getAllReservations(){
        return ResponseEntity.ok(this.reservationService.getAllReservations());
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
