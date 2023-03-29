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
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model){

//            @RequestParam(value = "from") String checkIn,
//            @RequestParam(value = "to") String checkOut,

        Room room = this.roomService.getRoomById(roomId);
        UserEntity user = this.userService.getByUsername(userDetails.getUsername()).get();

        model.addAttribute("user", user);
        model.addAttribute("roomType", room.getRoomType().name());

        model.addAttribute("roomId", roomId);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);

        System.out.println("I AM IN GET MAPPING " + roomId);
//        System.out.println(checkIn);
//        System.out.println(checkOut);

        return "reservation-add";
    }

    @PostMapping("/reservations/add")
    public String addReserve(@Valid ReservationDTO reservationDTO,
                             @RequestParam(value = "id") long roomId,
                             @RequestParam(value = "from") String checkIn,
                             @RequestParam(value = "to") String checkOut,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserDetails userDetails){



        System.out.println("In post mapping " + roomId);


        if(bindingResult.hasErrors() || !this.reservationService.saveReservation(reservationDTO, userDetails.getUsername()) ){

            redirectAttributes.addFlashAttribute("reservationDTO", reservationDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerDTO", bindingResult);
            model.addAttribute("id", roomId);
            model.addAttribute("checkIn", checkIn);
            model.addAttribute("checkOut", checkOut);

            String redirectURL = "redirect:/reservations/add?id=" + roomId + "&from=" + checkIn + "&to=" + checkOut;
            //?id=4&from=2023-03-30&to=2023-03-31
            System.out.println(redirectURL);

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


}
