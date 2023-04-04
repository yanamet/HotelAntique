package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.roomDTO.RoomViewDTO;
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



    public List<RoomViewDTO> getAllRoomTypes(){

        Room standardRoom = this.getByRoomType(RoomType.STANDARD);
        Room deluxeRoom = this.getByRoomType(RoomType.DELUXE);
        Room premiumRoom = this.getByRoomType(RoomType.PREMIUM);
        Room studio = this.getByRoomType(RoomType.STUDIO);
        Room apartment = this.getByRoomType(RoomType.APARTMENT);
        Room vip = this.getByRoomType(RoomType.VIP);
        Room president = this.getByRoomType(RoomType.PRESIDENT);

        List<Room> rooms = List.of(standardRoom, deluxeRoom, premiumRoom, studio, apartment, vip, president);

        return rooms.stream()
                .map(this::roomViewDtoMap)
                .collect(Collectors.toList());
    }

    public RoomViewDTO roomViewDtoMap(Room room){

        RoomViewDTO roomViewDTO = this.modelMapper.map(room, RoomViewDTO.class);
        roomViewDTO.setType(room.getRoomType().name());
        roomViewDTO.setDescription(room.getDescription());

        return roomViewDTO;

    }


    public void initRoomsData() {
        if(this.roomRepository.count() == 0){

            StringBuilder commonDescription = new StringBuilder();
            commonDescription.append("Bathroom").append(", ")
                    .append("AirConditioning").append(", ")
                    .append("Wifi").append(", ")
                    .append("Smart TV").append(", ")
                    .append("Parking with 24-hour video surveillance and controlled electronic access");


            StringBuilder standardDescription = createDescription(commonDescription, "Room Size: 20m2",
                    "Two separate beds with the option of a bedroom");

            Room firstStandard = this.createRoom("DOUBLE STANDARD ROOM", RoomType.STANDARD,
                    101, true, BigDecimal.valueOf(90.0), standardDescription.toString());

            Room secStandard = this.createRoom("DOUBLE STANDARD ROOM", RoomType.STANDARD,
                    102, true, BigDecimal.valueOf(90.0), standardDescription.toString());

            Room thirdStandard = this.createRoom("DOUBLE STANDARD ROOM", RoomType.STANDARD,
                    103, true, BigDecimal.valueOf(90.0),standardDescription.toString());

            commonDescription.append(", ").append("MiniBar");


            StringBuilder deluxeDescription = createDescription(commonDescription, "Room Size: 24m2",
                    "Two separate beds with the option of a bedroom");

            Room firstDeluxe = this.createRoom("DOUBLE DELUXE ROOM", RoomType.DELUXE,
                    104, true, BigDecimal.valueOf(100.0),deluxeDescription.toString());

            Room secondDeluxe = this.createRoom("DOUBLE DELUXE ROOM", RoomType.DELUXE,
                    105, true, BigDecimal.valueOf(100.0), deluxeDescription.toString());

            Room thirdDeluxe = this.createRoom("DOUBLE DELUXE ROOM", RoomType.DELUXE,
                    106, true, BigDecimal.valueOf(100.0), deluxeDescription.toString());


            StringBuilder premiumDescription = createDescription(commonDescription,
                    "Room Size: 28m2", "King-Size bed");

            Room firstPremium = this.createRoom("DOUBLE PREMIUM ROOM", RoomType.PREMIUM,
                    201, true, BigDecimal.valueOf(125.0), premiumDescription.toString());

            Room secondPremium = this.createRoom("DOUBLE PREMIUM ROOM", RoomType.PREMIUM,
                    202, true, BigDecimal.valueOf(125.0), premiumDescription.toString());


            StringBuilder studioDescription = createDescription(commonDescription, "Room Size: 32m2",
                    "Two separate beds with the option of a bedroom", "Upholstered furniture - sofa bed");

            Room firstStudio = this.createRoom("STUDIO", RoomType.STUDIO,
                    203, true, BigDecimal.valueOf(140.0), studioDescription.toString());

            Room seconedStudio = this.createRoom("STUDIO", RoomType.STUDIO,
                    204, true, BigDecimal.valueOf(140.0), studioDescription.toString());


            StringBuilder apartmentDescription = createDescription(commonDescription, "Room Size: 45m2",
                    "Living room with upholstered furniture - sofa bed",
                    "King-Size bed", "Kitchen box", "Terrace with a wonderful view");

            Room apartment = this.createRoom("APARTMENT", RoomType.APARTMENT,
                    301, true, BigDecimal.valueOf(160.0), apartmentDescription.toString());


            StringBuilder vipDescription = createDescription(commonDescription, "Room Size: 48m2",
                    "Living room set", "Kitchen box", "Illy capsule coffee machine");

            Room vip = this.createRoom("VIP APARTMENT", RoomType.VIP,
                    302, true, BigDecimal.valueOf(190.0), vipDescription.toString());


            StringBuilder presidentDescr = createDescription(commonDescription, "Room Size: 86m2",
                    "Two separate bedrooms", "Mattresses made of highly elastic HR foam",
                    "Living room set", "Kitchen box");
            Room president = this.createRoom("PRESIDENT APARTMENT", RoomType.PRESIDENT,
                    303, true, BigDecimal.valueOf(210.0), presidentDescr.toString());

            this.roomRepository.saveAll(List.of(
                    firstStandard, secStandard, thirdStandard,
                    firstDeluxe, secondDeluxe, thirdDeluxe,
                    firstPremium, secondPremium,
                    firstStudio, seconedStudio,
                    apartment,
                    vip,
                    president));


        }

    }

    private StringBuilder createDescription(StringBuilder commonDescription, String ...args){

        StringBuilder description = new StringBuilder();

        for (String current : args) {
            description.append(current).append(", ");
        }

        description.append(commonDescription);
        return description;

    }

    private Room createRoom(String roomName, RoomType roomType,
                            int roomNumber, boolean isAvailable,
                            BigDecimal price, String description ){
        Room room = new Room();
        room.setName(roomName);
        room.setRoomType(roomType);
        room.setRoomNumber(roomNumber);
        room.setAvailable(isAvailable);
        room.setPrice(price);
        room.setDescription(description);
        return room;
    }

    public Room getRoomById(long id) {
        return this.roomRepository.findById(id).get();
    }

    public RoomViewDTO getRoomDetailsViewById(long id) {
        return this.roomViewDtoMap(this.getRoomById(id));
    }

    public Room getByRoomType(RoomType roomType) {
        return this.roomRepository.findByRoomType(roomType).get(0);
    }

    public List<Room> getAllByRoomType(RoomType roomType){
        return this.roomRepository.findByRoomTypeAndIsAvailable(roomType, true);
    }

    public Room getRoomByRoomNumber(int i) {
        return this.roomRepository.findByRoomNumber(i);
    }

    public void saveRoom(Room room) {
        this.roomRepository.save(room);
    }
}
