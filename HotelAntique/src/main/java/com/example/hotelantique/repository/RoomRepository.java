package com.example.hotelantique.repository;

import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByRoomType(RoomType roomType);

    Room findByRoomNumber(int number);

    List<Room> findByRoomTypeAndIsAvailable(RoomType roomType, boolean isAvailable);
}
