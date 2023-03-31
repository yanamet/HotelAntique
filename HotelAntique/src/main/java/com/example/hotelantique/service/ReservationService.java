package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationDTO;
import com.example.hotelantique.model.dtos.reservationDTO.ReservationDetailsDTO;
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
import java.time.format.DateTimeFormatter;
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
    private final EmailService emailService;

    public ReservationService(ReservationRepository reservationRepository,
                              RoomService roomService, UserService userService,
                              PaymentService paymentService, ModelMapper modelMapper,
                              EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    public void saveReservation(ReservationDTO reservationDTO, String loggedUserUsername) {
        System.out.println("ReservationDTO " + reservationDTO);

        UserEntity user = this.userService.getByUsername(loggedUserUsername).get();

        Room room = this.roomService.getRoomByRoomNumber(reservationDTO.getRoomNumber());
        room.setAvailable(false);
        this.roomService.saveRoom(room);

        LocalDate checkIn = LocalDate.parse(reservationDTO.getCheckIn());
        LocalDate checkOut = LocalDate.parse(reservationDTO.getCheckOut());

        Reservation reservation = this.modelMapper.map(reservationDTO, Reservation.class);
        reservation.setGuest(user);
        reservation.setRoom(room);
        reservation.setTotalValue(room.getPrice());
        reservation.setCreatedOn(LocalDate.now());
        reservation.setCheckIn(checkIn);
        reservation.setCheckOut(checkOut);
        reservation.setActive(true);

        System.out.println("Reservation " + reservation);

        this.reservationRepository.save(reservation);

        this.emailService.sendSuccessfulReservationEmail(user.getFullName(), user.getEmail(), reservation);

    }



    public List<AvailableRoomFoundDTO> getAvailableRoomsInPeriod(LocalDate checkIn, LocalDate checkOut, String roomTypeString){
        RoomType roomType = RoomType.valueOf(roomTypeString);
        List<Room> roomToReserve = this.roomService.getAllByRoomType(roomType);

        if(roomToReserve.isEmpty()){
            List<Room> byTypeAndCheckInAndCheckOut = this.reservationRepository
                    .getByCheckInLessThanAndCheckOutGreaterThan(checkIn, checkOut, roomType);

            return mapListRoomToFoundAvailableDTOList(byTypeAndCheckInAndCheckOut, checkIn, checkOut);
        }
        return mapListRoomToFoundAvailableDTOList(roomToReserve, checkIn, checkOut);
    }

    private List<AvailableRoomFoundDTO> mapListRoomToFoundAvailableDTOList(List<Room> rooms,
                                                                           LocalDate checkIn, LocalDate checkOut){
        List<AvailableRoomFoundDTO> availableRoomFoundDTOList = new ArrayList<>();

        for (Room room : rooms) {
            AvailableRoomFoundDTO availableRoomFoundDTO = this.modelMapper.map(room, AvailableRoomFoundDTO.class);
            availableRoomFoundDTO.setCheckIn(checkIn);
            availableRoomFoundDTO.setCheckOut(checkOut);

            availableRoomFoundDTOList.add(availableRoomFoundDTO);
        }


        return  availableRoomFoundDTOList;
    }


    public void initReservationTries() {
        if(this.reservationRepository.count() == 0){

            Room room = this.roomService.getRoomByRoomNumber(101);
            room.setAvailable(false);
            this.roomService.saveRoom(room);

            Payment payment = new Payment();
            payment.setCvv("cvv");
            payment.setExpirationYear(1233);
            payment.setOwner("OWNER");
            payment.setCardNumber("cvv");
            payment.setExpirationMonth("cvv");

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




            Reservation reservation2 = new Reservation();
            reservation2.setGuest(admin);
            reservation2.setCheckIn(LocalDate.of(2023, 4, 25));
            reservation2.setCheckOut(LocalDate.of(2023, 4, 28));
            reservation2.setPayment(payment);
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
            reservation4.setPayment(payment);
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
            reservation5.setCheckOut(LocalDate.of(2023, 3, 28));
            reservation5.setPayment(payment);
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

    public ReservationDetailsDTO getReservationById(long id) {
        Reservation reservation = this.reservationRepository.findById(id).get();
        return this.modelMapper
                .map(reservation, ReservationDetailsDTO.class);
    }

    public void anulateReservation(long id) {
        Reservation reservation = this.reservationRepository.findById(id).get();
        reservation.setActive(false);

        Room room = reservation.getRoom();
        room.setAvailable(true);
        this.roomService.saveRoom(room);

        this.reservationRepository.save(reservation);
    }
}
