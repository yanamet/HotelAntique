package com.example.hotelantique.controller;

import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.enums.RoomType;
import com.example.hotelantique.repository.RoomRepository;
import com.example.hotelantique.service.ReservationServiceTest;
import com.example.hotelantique.service.RoomService;
import com.example.hotelantique.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationServiceTest reservationService;

    @MockBean
    private  RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private  UserService userService;
    Room room;
    RoomType roomType;

    @BeforeEach
    void setUp(){

        this.roomService = new RoomService(roomRepository, modelMapper);

        roomType = RoomType.STANDARD;
        room = new Room();
        room.setId(1L);
        room.setRoomType(roomType);
        room.setRoomNumber(101);
        room.setAvailable(true);
        room.setName("DOUBLE STANDARD");
        room.setPrice(BigDecimal.valueOf(90));
        this.roomService.saveRoom(room);

    }

    @Test
    @WithMockUser(username = "mock", roles = {"GUEST"})
     void testReservationPageIsShown() throws Exception {

//        "/reservations/add?id=1&from=2023-03-30&to=2023-04-02"

        mockMvc.perform(get("/reservations/add?id=1&from=2023-03-30&to=2023-04-02"))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation"));

    }

}
