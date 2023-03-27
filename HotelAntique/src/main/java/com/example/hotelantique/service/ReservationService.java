package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationDTO;
import com.example.hotelantique.model.dtos.reservationDTO.ReservationViewDTO;
import com.example.hotelantique.model.dtos.roomDTO.AvailableRoomFoundDTO;
import com.example.hotelantique.model.entity.Payment;
import com.example.hotelantique.model.entity.Reservation;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.model.enums.PaymentMethod;
import com.example.hotelantique.model.enums.RoomType;
import com.example.hotelantique.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final UserService userService;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    public ReservationService(ReservationRepository reservationRepository,
                              RoomService roomService, UserService userService,
                              PaymentService paymentService, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
    }

    public boolean saveReservation(ReservationDTO reservationDTO, String loggedUserUsername) {

        UserEntity user = this.userService.getByUsername(loggedUserUsername).get();

        Reservation reservation = this.modelMapper.map(reservationDTO, Reservation.class);


        System.out.println(reservation.toString());

//        roomToReserve.setAvailable(false);
//        this.roomService.saveRoom(roomToReserve);

        return true;
    }

    public List<AvailableRoomFoundDTO> getAvailableRoomsInPeriod(LocalDate checkIn, LocalDate checkOut, String roomTypeString){
        RoomType roomType = RoomType.valueOf(roomTypeString);
        List<Room> roomToReserve = this.roomService.getAllByRoomType(roomType);

        if(roomToReserve.isEmpty()){
            List<Room> byTypeAndCheckInAndCheckOut = this.reservationRepository
                    .getByCheckInLessThanAndCheckOutGreaterThan(checkIn, checkOut, roomType);

            return mapListRoomToFoundAvailableDTOList(byTypeAndCheckInAndCheckOut);
        }
        return mapListRoomToFoundAvailableDTOList(roomToReserve);
    }

    private List<AvailableRoomFoundDTO> mapListRoomToFoundAvailableDTOList(List<Room> rooms){
       return  rooms.stream()
                .map(r -> this.modelMapper.map(r, AvailableRoomFoundDTO.class))
                .collect(Collectors.toList());
    }


    public void initReservationTries() {
        if(this.reservationRepository.count() == 0){

            Room room = this.roomService.getRoomByRoomNumber(101);
            room.setAvailable(false);
            this.roomService.saveRoom(room);

            Payment payment = new Payment();
            payment.setPaymentMethod(PaymentMethod.CASH);

            this.paymentService.savePayment(payment);

            UserEntity admin = this.userService.getByUsername("Admin").get();
            Reservation reservation = new Reservation();
            reservation.setGuest(admin);
            reservation.setCheckIn(LocalDate.of(2023, 4, 23));
            reservation.setCheckOut(LocalDate.of(2023, 4, 27));
            reservation.setPayment(payment);
            reservation.setActive(true);
            reservation.setCreatedOn(LocalDate.now());
            reservation.setTotalValue(BigDecimal.valueOf(90.0));
            reservation.setRoom(room);

            this.reservationRepository.save(reservation);

            Room room2 = this.roomService.getRoomByRoomNumber(102);
            room2.setAvailable(false);
            this.roomService.saveRoom(room2);


            Payment payment2 = new Payment();
            payment2.setPaymentMethod(PaymentMethod.CASH);

            this.paymentService.savePayment(payment2);

            Reservation reservation2 = new Reservation();
            reservation2.setGuest(admin);
            reservation2.setCheckIn(LocalDate.of(2023, 4, 25));
            reservation2.setCheckOut(LocalDate.of(2023, 4, 28));
            reservation2.setPayment(payment2);
            reservation2.setActive(true);
            reservation2.setCreatedOn(LocalDate.now());
            reservation2.setTotalValue(BigDecimal.valueOf(90.0));
            reservation2.setRoom(room2);

            this.reservationRepository.save(reservation2);

            Room room4 = this.roomService.getRoomByRoomNumber(303);
            room4.setAvailable(false);
            this.roomService.saveRoom(room4);


            Reservation reservation4 = new Reservation();
            reservation4.setGuest(admin);
            reservation4.setCheckIn(LocalDate.of(2023, 4, 22));
            reservation4.setCheckOut(LocalDate.of(2023, 4, 24));
            reservation4.setPayment(payment2);
            reservation4.setActive(true);
            reservation4.setCreatedOn(LocalDate.now());
            reservation4.setTotalValue(BigDecimal.valueOf(90.0));
            reservation4.setRoom(room4);

            this.reservationRepository.save(reservation4);

            Reservation reservation5 = new Reservation();

            Room room5 = this.roomService.getRoomByRoomNumber(103);
            room5.setAvailable(false);
            this.roomService.saveRoom(room5);

            reservation5.setGuest(admin);
            reservation5.setCheckIn(LocalDate.of(2023, 3, 20));
            reservation5.setCheckOut(LocalDate.of(2023, 3, 27));
            reservation5.setPayment(payment2);
            reservation5.setActive(true);
            reservation5.setCreatedOn(LocalDate.now());
            reservation5.setTotalValue(BigDecimal.valueOf(90.0));
            reservation5.setRoom(room5);

            this.reservationRepository.save(reservation5);



        }

    }

    public List<ReservationViewDTO> getAllReservations() {
        List<Reservation> reservations = this.reservationRepository.findByIsActiveOrderByCheckInAsc(true);
        return reservations
                .stream()
                .map(this::reservationViewMapper)
                .collect(Collectors.toList());
    }

    private ReservationViewDTO reservationViewMapper(Reservation reservation){
        ReservationViewDTO reservationViewDTO = this.modelMapper
                .map(reservation, ReservationViewDTO.class);

        reservationViewDTO.setId(reservation.getId());
        reservationViewDTO.setGuestUsername(reservation.getGuest().getUsername());
        reservationViewDTO.setGuestFullName(reservation.getGuest().getFullName());
        reservationViewDTO.setCheckInAndCheckOut(reservation.getCheckIn() + "-" + reservation.getCheckOut());

        return reservationViewDTO;

    }

    public List<ReservationViewDTO> getPreviousReservations(UserEntity user, LocalDate today) {
        return this.reservationRepository
                .findByGuestAndCheckInBefore(user, today)
                .stream()
                .map(this::reservationViewMapper)
                .collect(Collectors.toList());
    }

    public List<ReservationViewDTO> getUpcomingReservations(UserEntity user, LocalDate today) {
        return this.reservationRepository
                .findByGuestAndCheckInAfterAndIsActive(user, today, true)
                .stream()
                .map(this::reservationViewMapper)
                .collect(Collectors.toList());
    }

    public void deactivatePastReservations() {

        List<Reservation>  reservations = this.reservationRepository
              .findByIsActiveAndCheckOutBefore(true, LocalDate.now());

        for (Reservation reservation : reservations) {
            reservation.setActive(false);

            Room room = reservation.getRoom();
            room.setAvailable(true);

            this.reservationRepository.save(reservation);
            this.roomService.saveRoom(room);
        }

    }
}
