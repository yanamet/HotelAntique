package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationDetailsDTO;
import com.example.hotelantique.model.dtos.reservationDTO.ReservationViewDTO;
import com.example.hotelantique.model.dtos.userDTO.UserAdminPageDTO;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.service.ReservationService;
import com.example.hotelantique.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;

    public UserController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping("/user/profile")
    public String myProfile(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {

        UserEntity user = this.userService.getByUsername(userDetails.getUsername()).get();

        model.addAttribute("user", user);

        List<ReservationViewDTO> previousReservations = this.reservationService
                .getPreviousReservations(user, LocalDate.now());

        List<ReservationViewDTO> upcomingReservations = this.reservationService
                .getUpcomingReservations(user, LocalDate.now());

        model.addAttribute("previousReservations", previousReservations);
        model.addAttribute("upcomingReservations", upcomingReservations);


        return "my-profile";
    }

    @GetMapping("/pages/admin")
    public String adminPage(@AuthenticationPrincipal UserDetails userDetails
            , Model model) {

        UserEntity adminUser = this.userService
                .getByUsername(userDetails.getUsername()).get();


        List<UserAdminPageDTO> allUsersOtherThan = this.userService
                .getAllUsersOtherThan(adminUser.getId());

        model.addAttribute("otherUsers", allUsersOtherThan);


        return "admin-page";
    }

    @PreAuthorize("@reservationService.isOwner(#userDetails, #id)")
    @GetMapping("/user/reservations/details/{id}")
    public String reservationsDetails(@AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable("id") long id,
                                      Model model) {
        ReservationDetailsDTO reservation = this.reservationService.getReservationById(id);
        model.addAttribute("reservation", reservation);
        return "reservation-details";
    }

    @PreAuthorize("@reservationService.isOwner(#userDetails, #id)")
    @GetMapping("/user/reservations/cancel/{id}")
    public String cancelReservation(@AuthenticationPrincipal UserDetails userDetails,
                                    @PathVariable("id") long id) {
        this.reservationService.cancelReservation(id);
        return "redirect:/user/profile";
    }

    @GetMapping("/access/denied")
    public String accessDenied(){
        return "access-denied";
    }


}
