package com.example.hotelantique.repository;

import com.example.hotelantique.model.entity.Reservation;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.model.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    @Query("SELECT r.room FROM Reservation r WHERE r.room.roomType = :roomType AND (:checkIn NOT BETWEEN r.checkIn AND r.checkOut) AND (:checkOut NOT BETWEEN r.checkIn AND r.checkOut)")
    List<Room> getByCheckInLessThanAndCheckOutGreaterThan(@Param("checkIn") LocalDate checkIn,
                                                                 @Param("checkOut") LocalDate checkOut,
                                                                 @Param("roomType") RoomType roomType);

    List<Reservation> findByIsActiveOrderByCheckInAsc(boolean b);

    List<Reservation> findByGuestAndCheckInBefore(UserEntity user, LocalDate today);

    List<Reservation> findByGuestAndCheckInAfterAndIsActive(UserEntity user, LocalDate today, boolean isActive);

    List<Reservation> findByIsActiveAndCheckOutBefore(boolean isActive, LocalDate now);
}
