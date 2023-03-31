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
import org.springframework.web.bind.annotation.*;
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




    @GetMapping("/reservations/add")
    public String addReserve(
                             @RequestParam(value = "id") long roomId,
                             @RequestParam(value = "from") String checkIn,
                             @RequestParam(value = "to") String checkOut,
                             Model model){
        Room room = this.roomService.getRoomById(roomId);

        model.addAttribute("roomType", room.getRoomType().name());

        model.addAttribute("roomNumberSearch", room.getRoomNumber());
        model.addAttribute("checkInSearch", checkIn);
        model.addAttribute("checkOutSearch", checkOut);


        return "reservation-add";
    }


    @PostMapping("/reservations/add")
    public String addReserve(@Valid ReservationDTO reservationDTO,

                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserDetails userDetails){

        System.out.println("ReservationDTO " + reservationDTO);

        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("reservationDTO", reservationDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerDTO", bindingResult);
//            return "redirect:/reservations/add";

            return "redirect:/reservations/error";
        }

        this.reservationService.saveReservation(reservationDTO, userDetails.getUsername());

        return "successful-reservation";
    }

    @GetMapping("/reservations/error")
    public String reservationErrorMissedField(){
        return "error-reservation-missed-field";
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


}
