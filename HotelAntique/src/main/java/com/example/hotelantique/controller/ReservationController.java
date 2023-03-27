package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationDTO;
import com.example.hotelantique.model.dtos.reservationDTO.ReservationDetailsDTO;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.service.ReservationService;
import com.example.hotelantique.service.RoomService;
import com.example.hotelantique.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomService roomService;
    private final UserService userService;

    public ReservationController(ReservationService reservationService,
                                 RoomService roomService, UserService userService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.userService = userService;
    }

    @ModelAttribute("reservationDTO")
    public ReservationDTO initReservationDTO(){
        return new ReservationDTO();
    }

    @GetMapping("/reservations/add/{id}")
    public String addReserve(@PathVariable("id") long roomId,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model){
        Room room = this.roomService.getRoomById(roomId);
        UserEntity user = this.userService.getByUsername(userDetails.getUsername()).get();

        model.addAttribute("user", user);
        model.addAttribute("roomType", room.getRoomType().name());

        return "reservation-add";
    }

    @PostMapping("/reservations/add/{id}")
    public String addReserve(@Valid ReservationDTO reservationDTO,
                             @PathVariable("id") long roomId,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserDetails userDetails){

        System.out.println(reservationDTO);

        if(bindingResult.hasErrors() || !this.reservationService.saveReservation(reservationDTO, userDetails.getUsername()) ){


            redirectAttributes.addFlashAttribute("reservationDTO", reservationDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerDTO", bindingResult);

            return "redirect:/reservations/add";
        }

        return "successful-reservation";
    }

    @GetMapping("/reservations/successful")
    public String successfulReservation(){
        return "successful-reservation";
    }

    @GetMapping("/reservations/details/{id}")
    public String reservationsDetails(@PathVariable("id") long id,
                                      Model model){
        ReservationDetailsDTO reservation =  this.reservationService.getReservationById(id);
        model.addAttribute("reservation", reservation);
        return "reservation-details";
    }

    @GetMapping("/reservations/anulate/{id}")
    public String anulateReservation(@PathVariable("id") long id){
        this.reservationService.anulateReservation(id);
        return "redirect:/user/profile";
    }

    @GetMapping("/payment/card")
    public String cardPayment(){
        return "card-details";
    }

}
