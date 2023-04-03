package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationDTO;
import com.example.hotelantique.model.dtos.reservationDTO.ReservationDetailsDTO;
import com.example.hotelantique.model.dtos.reservationDTO.ReservationViewDTO;
import com.example.hotelantique.model.entity.Payment;
import com.example.hotelantique.model.entity.Reservation;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.model.enums.RoomType;
import com.example.hotelantique.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    ReservationService toTest;

    @Mock
    ReservationRepository reservationRepository;
    @Mock
    UserService userService;

    @Mock
    ModelMapper mockModelMapper;
    @Mock
    EmailService mockEmailService;
    @Mock
    RoomService roomService;

    @Captor
    ArgumentCaptor<Reservation> reservationArgumentCaptor;

    UserEntity user;
    Reservation reservation;
    ReservationDTO reservationDTO;
    Room room;

    @BeforeEach
    void setUp() {
        toTest = new ReservationService(reservationRepository, roomService,
                userService,  mockModelMapper, mockEmailService);

        user = new UserEntity();
        user.setRoles(Collections.emptyList());
        user.setUsername("Username");
        user.setFullName("Test Name");
        user.setEmail("test@email.com");
        user.setPassword("topsecret");
        user.setGender("MALE");
        user.setPhoneNumber("0000000000");

        room = new Room();
        room.setPrice(BigDecimal.valueOf(90));
        room.setRoomNumber(101);
        room.setId(1L);
        room.setAvailable(true);
        room.setName("DOUBLE STANDARD TEST");
        room.setRoomType(RoomType.STANDARD);

        reservation = new Reservation();
        reservation.setActive(true);
        reservation.setCheckIn(LocalDate.of(2023, 4, 10));
        reservation.setCheckOut(LocalDate.of(2023, 4, 12));
        reservation.setRoom(room);
        reservation.setCreatedOn(LocalDate.now());
        reservation.setGuest(user);
        reservation.setId(1L);

        reservationDTO = new ReservationDTO();
        reservationDTO.setCheckIn("2023-04-10");
        reservationDTO.setCheckOut("2023-04-12");
        reservationDTO.setEmail("test@email.com");
        reservationDTO.setRoomNumber(101);
        reservationDTO.setFullName("Test Name");
        reservationDTO.setRoomType("STANDARD");

    }

    @Test
    void reservationServiceSavedReservationSuccessfully() {
        when(this.userService.getByUsername("Username"))
                .thenReturn(Optional.of(user));

        when(this.roomService.getRoomByRoomNumber(101))
                .thenReturn(room);

        when(mockModelMapper.map(reservationDTO, Reservation.class))
                .thenReturn(reservation);

        toTest.saveReservation(reservationDTO, "Username");


        Assertions.assertEquals(room, this.roomService.getRoomByRoomNumber(101));
        Assertions.assertEquals(reservation, mockModelMapper.map(reservationDTO, Reservation.class));
        Assertions.assertEquals(Optional.of(user), this.userService.getByUsername("Username"));

        verify(reservationRepository).save(reservationArgumentCaptor.capture());

    }

    @Test
    void getAllReservationsReturnsTheCorrectValueWhenReservationsActive() {

        when(this.reservationRepository.findByIsActiveOrderByCheckInAsc(true))
                .thenReturn(List.of(reservation));

        ReservationViewDTO reservationViewDTO = new ReservationViewDTO();
        reservationViewDTO.setCheckInAndCheckOut(reservation.getCheckIn() + "-" + reservation.getCheckOut());
        reservationViewDTO.setGuestFullName(user.getFullName());
        reservationViewDTO.setActive(true);
        reservationViewDTO.setGuestUsername(user.getUsername());

        when(mockModelMapper.map(reservation, ReservationViewDTO.class))
                .thenReturn(reservationViewDTO);

        List<ReservationViewDTO> allReservations = toTest.getAllReservations();

        Assertions.assertEquals(1, allReservations.size());
        Assertions.assertEquals(reservationViewDTO.getGuestFullName(), allReservations.get(0).getGuestFullName());
        Assertions.assertEquals(reservationViewDTO.getGuestUsername(), allReservations.get(0).getGuestUsername());
        Assertions.assertEquals(reservationViewDTO.getCheckInAndCheckOut(),
                allReservations.get(0).getCheckInAndCheckOut());

    }

    @Test
    void getPreviousReservationsReturnTheCorrectValue() {

        when(this.reservationRepository
                .findByGuestAndCheckInBefore(user, LocalDate.of(2023, 4, 13)))
                .thenReturn(List.of(reservation));

        ReservationViewDTO reservationViewDTO = new ReservationViewDTO();
        reservationViewDTO.setCheckInAndCheckOut(reservation.getCheckIn() + "-" + reservation.getCheckOut());
        reservationViewDTO.setGuestFullName(user.getFullName());
        reservationViewDTO.setActive(true);
        reservationViewDTO.setGuestUsername(user.getUsername());

        when(mockModelMapper.map(reservation, ReservationViewDTO.class))
                .thenReturn(reservationViewDTO);

        List<ReservationViewDTO> previousReservations = this.toTest
                .getPreviousReservations(user, LocalDate.of(2023, 4, 13));

        Assertions.assertEquals(1, previousReservations.size());

        Assertions.assertEquals(reservationViewDTO.getGuestFullName(),
                previousReservations.get(0).getGuestFullName());

        Assertions.assertEquals(reservationViewDTO.getCheckInAndCheckOut(),
                previousReservations.get(0).getCheckInAndCheckOut());

        Assertions.assertEquals(reservationViewDTO.getGuestUsername(),
                previousReservations.get(0).getGuestUsername());


    }

    @Test
    void getUpcomingReservationsReturnTheCorrectValue() {

        when(this.reservationRepository
                .findByGuestAndCheckInAfterAndIsActive(user,
                        LocalDate.of(2023, 4, 9),
                        true))
                .thenReturn(List.of(reservation));

        ReservationViewDTO reservationViewDTO = new ReservationViewDTO();
        reservationViewDTO.setCheckInAndCheckOut(reservation.getCheckIn() + "-" + reservation.getCheckOut());
        reservationViewDTO.setGuestFullName(user.getFullName());
        reservationViewDTO.setActive(true);
        reservationViewDTO.setGuestUsername(user.getUsername());


        when(mockModelMapper.map(reservation, ReservationViewDTO.class))
                .thenReturn(reservationViewDTO);

        List<ReservationViewDTO> upcomingReservations = toTest
                .getUpcomingReservations(user, LocalDate.of(2023, 4, 9));

        Assertions.assertEquals(1, upcomingReservations.size());

        Assertions.assertEquals(reservationViewDTO.getGuestFullName(),
                upcomingReservations.get(0).getGuestFullName());

        Assertions.assertEquals(reservationViewDTO.getCheckInAndCheckOut(),
                upcomingReservations.get(0).getCheckInAndCheckOut());

        Assertions.assertEquals(reservationViewDTO.getGuestUsername(),
                upcomingReservations.get(0).getGuestUsername());
    }

    @Test
    void testDeactivateReservationsCorrectly(){

        when(this.reservationRepository
                .findByIsActiveAndCheckOutBefore(true, LocalDate.now()))
                .thenReturn(List.of(reservation));

        this.toTest.deactivatePastReservations();
         verify(reservationRepository).save(reservationArgumentCaptor.capture());

        Assertions.assertTrue(reservation.getRoom().isAvailable());
        verify(this.roomService).saveRoom(any());

    }

    @Test
    void anulateReservationChangesTheReservationAndRoomStatus(){

        when(this.reservationRepository.findById(1L))
                .thenReturn(Optional.of(reservation));

        this.toTest.anulateReservation(1L);
        Assertions.assertTrue(reservation.getRoom().isAvailable());
        Assertions.assertFalse(reservation.isActive());

    }

    @Test
    void getReservationByIdReturnsRightReservationDetails(){

        when(this.reservationRepository.findById(1L))
                .thenReturn(Optional.of(reservation));

        ReservationDetailsDTO reservationDetailsDTO = new ReservationDetailsDTO();
        reservationDetailsDTO.setActive(reservation.isActive());
        reservationDetailsDTO.setCheckIn(reservation.getCheckIn());
        reservationDetailsDTO.setId(reservation.getId());
        reservationDetailsDTO.setRoomType(reservation.getRoom().getRoomType().name());
        reservationDetailsDTO.setGuestUsername(reservation.getGuest().getUsername());
        reservationDetailsDTO.setCheckOut(reservation.getCheckOut());

        when(this.mockModelMapper.map(reservation, ReservationDetailsDTO.class))
                .thenReturn(reservationDetailsDTO);

        ReservationDetailsDTO reservationById = toTest.getReservationById(1L);

        Assertions.assertEquals(1L, reservationById.getId());
        Assertions.assertEquals("STANDARD", reservationById.getRoomType());
        Assertions.assertEquals("Username", reservationById.getGuestUsername());

    }

    @Test
    void createReservationReturnTheCorrectReservationDTO(){

        ReservationDTO reservationDTO1 = this.toTest
                .createReservationDTO(room, "2023-04-04", "2023-04-05");
        Assertions.assertEquals(101, reservationDTO1.getRoomNumber());
        Assertions.assertEquals("STANDARD", reservationDTO1.getRoomType());
        Assertions.assertEquals(1L, reservationDTO1.getRoomId());
    }

    @Test
    void getReservationReturnsTheRightReservation(){
        when(this.reservationRepository.findById(1L))
                .thenReturn(Optional.of(reservation));
       Reservation  toTestReservation = toTest.getReservation(1L);
       Assertions.assertEquals(reservation, toTestReservation);
    }


}
