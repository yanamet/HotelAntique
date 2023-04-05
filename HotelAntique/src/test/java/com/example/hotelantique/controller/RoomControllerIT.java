package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.roomDTO.RoomViewDTO;
import com.example.hotelantique.service.ReservationService;
import com.example.hotelantique.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @MockBean
    private ReservationService reservationService;

    private RoomViewDTO roomViewDTO;

    @BeforeEach
    void setUp(){
        roomViewDTO = new RoomViewDTO();
        roomViewDTO.setId(1L);
        roomViewDTO.setType("STANDARD");
        roomViewDTO.setDescription("Descr");
        roomViewDTO.setPrice(BigDecimal.valueOf(90));
        roomViewDTO.setName("Double Standard");
    }


    @Test
    @WithMockUser(username = "GUEST", roles = {"GUEST"})
    void getAllRoomsReturnsTheCorrectListOfRoomViewDTOs() throws Exception {
        this.mockMvc.perform(get("/rooms/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allRooms"))
                .andExpect(view().name("all-rooms"));
    }

    @Test
    @WithMockUser(username = "GUEST", roles = {"GUEST"})
    void getRoomDetails() throws Exception {

        when(this.roomService.getRoomDetailsViewById(1L)).thenReturn(roomViewDTO);

        this.mockMvc.perform(get("/rooms/details/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("room"))
                .andExpect(view().name("details"));
    }

}
