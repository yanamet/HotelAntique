package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.RoomViewDTO;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.enums.RoomType;
import com.example.hotelantique.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public RoomService(RoomRepository roomRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    public List<RoomViewDTO> getAllRooms(){
        List<Room> allRooms = this.roomRepository.findAll();
        return allRooms
                .stream()
                .map(this::roomViewDtoMap)
                .collect(Collectors.toList());
    }

    public RoomViewDTO roomViewDtoMap(Room room){

        RoomViewDTO roomViewDTO = this.modelMapper.map(room, RoomViewDTO.class);
        roomViewDTO.setType(room.getRoomType().name());

        return roomViewDTO;

    }


    public void initRoomsData() {
        if(this.roomRepository.count() == 0){
            Room standard = this.createRoom("DOUBLE STANDARD ROOM", RoomType.STANDARD,
                    101, true, BigDecimal.valueOf(90.0));

            Room deluxe = this.createRoom("DOUBLE DELUXE ROOM", RoomType.DELUXE,
                    102, true, BigDecimal.valueOf(100.0));

            Room premium = this.createRoom("DOUBLE PREMIUM ROOM", RoomType.PREMIUM,
                    103, true, BigDecimal.valueOf(125.0));

            Room studio = this.createRoom("STUDIO", RoomType.STUDIO,
                    201, true, BigDecimal.valueOf(140.0));

            Room apartment = this.createRoom("APARTMENT", RoomType.APARTMENT,
                    202, true, BigDecimal.valueOf(160.0));

            Room vip = this.createRoom("VIP APARTMENT", RoomType.VIP,
                    301, true, BigDecimal.valueOf(190.0));

            Room president = this.createRoom("PRESIDENT APARTMENT", RoomType.PRESIDENT,
                    302, true, BigDecimal.valueOf(210.0));

            this.roomRepository.saveAll(List.of(standard, deluxe, premium, studio, apartment, vip, president));

        }

    }

    private Room createRoom(String roomName, RoomType roomType,
                            int roomNumber, boolean isAvailable, BigDecimal price){
        Room room = new Room();
        room.setName(roomName);
        room.setRoomType(roomType);
        room.setRoomNumber(roomNumber);
        room.setAvailable(isAvailable);
        room.setPrice(price);
        return room;
    }

}
