package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.RoomViewDTO;
import com.example.hotelantique.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms/all")
    public String allRooms(Model model){

        List<RoomViewDTO> allRooms = this.roomService.getAllRooms();
        model.addAttribute("allRooms", allRooms);

        return "all-rooms";
    }



}
