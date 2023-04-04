package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationDTO;
import com.example.hotelantique.model.dtos.reservationDTO.ReservationDetailsDTO;
import com.example.hotelantique.model.dtos.reservationDTO.ReservationViewDTO;
import com.example.hotelantique.model.dtos.roomDTO.AvailableRoomFoundDTO;
import com.example.hotelantique.model.entity.Reservation;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.model.enums.RoleEnum;
import com.example.hotelantique.model.enums.RoomType;
import com.example.hotelantique.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final UserService userService;
//    private final PaymentService paymentService;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

//    PaymentService paymentService,]
//        this.paymentService = paymentService;


    public ReservationService(ReservationRepository reservationRepository,
                              RoomService roomService, UserService userService,
                               ModelMapper modelMapper,
                              EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.userService = userService;
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


    public boolean isOwner(UserDetails userDetails, long id){

        if (userDetails == null) {
            return  false;
        }

        Reservation reservation = this.getReservation(id);

        return (reservation.getGuest().getUsername().equals(userDetails.getUsername())) ||
                this.isUserAdmin(userDetails);

    }

    private boolean isUserAdmin(UserDetails userDetails){
        return userDetails.getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                anyMatch(a -> a.equals("ROLE_" + RoleEnum.ADMIN.name()));
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

    public void cancelReservation(long id) {
        Reservation reservation = this.reservationRepository.findById(id).get();
        reservation.setActive(false);

        Room room = reservation.getRoom();
        room.setAvailable(true);
        this.roomService.saveRoom(room);

        this.reservationRepository.save(reservation);
    }



    public Reservation getReservation(long id) {
        return this.reservationRepository.findById(id).get();
    }

    public void updateReservation(Reservation reservation) {
        this.reservationRepository.save(reservation);
    }

    public ReservationDTO createReservationDTO(Room room, String checkIn, String checkOut) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setRoomType(room.getRoomType().name());
        reservationDTO.setRoomNumber(room.getRoomNumber());
        reservationDTO.setRoomId(room.getId());
        reservationDTO.setCheckIn(checkIn);
        reservationDTO.setCheckOut(checkOut);
        return reservationDTO;
    }
}
