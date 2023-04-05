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

    public ReservationController(ReservationService reservationService,
                                 RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    @ModelAttribute("reservationDTO")
    public ReservationDTO initReservationDTO(){
        return new ReservationDTO();
    }


//    @RequestParam(value = "id") long roomId,
//    @RequestParam(value = "from") String checkIn,
//    @RequestParam(value = "to") String checkOut,

    @GetMapping("/reservations/add/{id}/{checkIn}/{checkOut}")
    public String addReserve(@PathVariable("id") long roomId,
                             @PathVariable("checkIn") String checkIn,
                             @PathVariable("checkOut")  String checkOut,

                             Model model){
        Room room = this.roomService.getRoomById(roomId);

       ReservationDTO reservationDTO =  this.reservationService
               .createReservationDTO(room, checkIn, checkOut);

        model.addAttribute("roomId", room.getId());

        model.addAttribute("reservationDTO", reservationDTO);

        return "reservation-add";
    }


    @PostMapping("/reservations/add/{id}/{checkIn}/{checkOut}")
    public String addReserve(@Valid ReservationDTO reservationDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserDetails userDetails){


        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("reservationDTO", reservationDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerDTO", bindingResult);

            return "redirect:/reservations/add/{id}/{checkIn}/{checkOut}";
        }

        this.reservationService.saveReservation(reservationDTO, userDetails.getUsername());

        return "redirect:/reservations/successful";
    }



    @GetMapping("/reservations/successful")
    public String successfulReservation(){
        return "successful-reservation";
    }




}
