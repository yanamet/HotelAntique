package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationDTO;
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
import java.util.List;

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

        System.out.println("I AM IN ");


        UserEntity user = this.userService.getByUsername(loggedUserUsername).get();

        RoomType roomType = RoomType.valueOf(reservationDTO.getRoomType());
        List<Room> roomToReserve = this.roomService.getAllByRoomType(roomType);

        if(roomToReserve.isEmpty()){
            List<Room> byTypeAndCheckInAndCheckOut = this.reservationRepository
                    .getByCheckInLessThanAndCheckOutGreaterThan(reservationDTO.getCheckIn(), reservationDTO.getCheckOut(), roomType);

            System.out.println("Hello");

        }

        Room room = roomToReserve.get(0);
        System.out.println(room);

        Reservation reservation = this.modelMapper.map(reservationDTO, Reservation.class);


        System.out.println(reservation.toString());

//        roomToReserve.setAvailable(false);
//        this.roomService.saveRoom(roomToReserve);

        return true;
    }

//    public List<Room> getAllReservation(){
//       return this.reservationRepository.getByTypeAndCheckInAndCheckOut(RoomType.STANDARD,
//                LocalDate.of(2023, 4, 24),
//                LocalDate.of(2023, 4, 28));
//
//    }

    public void initReservationTries() {
        if(this.reservationRepository.count() == 0){

            Room room = this.roomService.getRoomByRoomNumber(101);
            room.setAvailable(false);
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



        }

    }
}
