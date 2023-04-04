package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationDTO;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.enums.RoomType;
import com.example.hotelantique.repository.ReservationRepository;
import com.example.hotelantique.repository.RoomRepository;
import com.example.hotelantique.service.*;
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
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
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
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private EmailService emailService;

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

    ReservationDTO reservationDTO;

    @BeforeEach
    void setUp(){
//
//        this.reservationService = new ReservationService(reservationRepository, roomService, userService,
//                modelMapper, emailService );
//
//        this.roomService = new RoomService(roomRepository, modelMapper);

        roomType = RoomType.STANDARD;
        room = new Room();
        room.setRoomType(roomType);
        room.setRoomNumber(101);
        room.setId(1L);
        room.setAvailable(true);
        room.setName("DOUBLE STANDARD");
        room.setPrice(BigDecimal.valueOf(90));

        this.roomRepository.save(room);

        when(this.roomRepository.findById(1L))
                .thenReturn(Optional.of(room));
//        doReturn(Optional.of(room)).when(this.roomRepository).findById(1L);

        when(this.roomService.getRoomById(1L))
                .thenReturn(room);


    }



    @Test
    @WithMockUser(username = "GUEST", roles = {"GUEST"})
     void testReservationPageIsShown() throws Exception {



//        Optional<Room> byId = this.roomRepository.findById(1L);


//        "/reservations/add?id=1&from=2023-03-30&to=2023-04-02"

        mockMvc.perform(get("/reservations/add/{id}/{checkIn}/{checkOut}",
                        1L, "2023-04-06", "2023-04-08"))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation"));

    }

}
