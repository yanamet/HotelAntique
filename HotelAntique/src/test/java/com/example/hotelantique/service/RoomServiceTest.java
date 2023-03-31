package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.roomDTO.RoomViewDTO;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.enums.RoomType;
import com.example.hotelantique.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository mockRoomRepository;

    @Mock
    private ModelMapper mockModelMapper;
    private RoomService toTest;

    Room standardRoomTest = new Room();
    final static long STANDARD_ROOM_ID = 1L;
    final static int STANDARD_ROOM_NUMBER = 101;
    Room deluxeRoomTest = new Room();

    final static long DELUXE_ROOM_ID = 2L;
    final static int DELUXE_ROOM_NUMBER = 102;
    Room premiumRoomTest = new Room();
    final static long PREMIUM_ROOM_ID = 3L;
    final static int PREMIUM_ROOM_NUMBER = 103;
    Room studioRoomTest = new Room();
    final static long STUDIO_ROOM_ID = 4L;
    final static int STUDIO_ROOM_NUMBER = 104;
    Room apartmentRoomTest = new Room();
    final static long APARTMENT_ROOM_ID = 5L;
    final static int APARTMENT_ROOM_NUMBER = 105;
    Room vipRoomTest = new Room();
    final static long VIP_ROOM_ID = 6L;
    final static int VIP_ROOM_NUMBER = 106;
    Room presidentRoomTest = new Room();
    final static long PRESIDENT_ROOM_ID = 7L;
    final static int PRESIDENT_ROOM_NUMBER = 107;




    @BeforeEach
    public void setUp(){

        standardRoomTest.setId(1L);
        standardRoomTest.setRoomType(RoomType.STANDARD);
        standardRoomTest.setRoomNumber(101);

        deluxeRoomTest.setId(2L);
        deluxeRoomTest.setRoomType(RoomType.DELUXE);
        deluxeRoomTest.setRoomNumber(102);

        premiumRoomTest.setId(3L);
        premiumRoomTest.setRoomType(RoomType.DELUXE);
        premiumRoomTest.setRoomNumber(103);

        studioRoomTest.setId(4L);
        studioRoomTest.setRoomType(RoomType.STUDIO);
        studioRoomTest.setRoomNumber(104);

        apartmentRoomTest.setId(5L);
        apartmentRoomTest.setRoomType(RoomType.APARTMENT);
        apartmentRoomTest.setRoomNumber(105);

        vipRoomTest.setId(6L);
        vipRoomTest.setRoomType(RoomType.VIP);
        vipRoomTest.setRoomNumber(106);

        presidentRoomTest.setId(7L);
        presidentRoomTest.setRoomType(RoomType.PRESIDENT);
        presidentRoomTest.setRoomNumber(107);

        toTest = new RoomService(mockRoomRepository, mockModelMapper);
    }

    @Test
    void rightStandardRoomIsReturnedByTypeNumberAndId(){

        when(mockRoomRepository.findByRoomType(RoomType.STANDARD))
                .thenReturn(List.of(standardRoomTest));
        when(mockRoomRepository.findByRoomNumber(STANDARD_ROOM_NUMBER))
                .thenReturn(standardRoomTest);
        when(mockRoomRepository.findById(STANDARD_ROOM_ID))
                .thenReturn(Optional.of(standardRoomTest));

        List<Room> byRoomTypeStandard = this.mockRoomRepository.findByRoomType(RoomType.STANDARD);
        Room byRoomNumber = this.mockRoomRepository.findByRoomNumber(STANDARD_ROOM_NUMBER);
        Room roomById = this.mockRoomRepository.findById(STANDARD_ROOM_ID).get();

        Assertions.assertEquals(1, byRoomTypeStandard.size());
        Assertions.assertEquals(standardRoomTest.getRoomNumber(), byRoomTypeStandard.get(0).getRoomNumber());

        Assertions.assertEquals(byRoomNumber, standardRoomTest);

        Assertions.assertEquals(roomById, standardRoomTest);

    }

    @Test
    void rightDeluxeRoomIsReturnedByTypeNumberAndId(){

        when(mockRoomRepository.findByRoomType(RoomType.DELUXE))
                .thenReturn(List.of(deluxeRoomTest));
        when(mockRoomRepository.findByRoomNumber(DELUXE_ROOM_NUMBER))
                .thenReturn(deluxeRoomTest);
        when(mockRoomRepository.findById(DELUXE_ROOM_ID))
                .thenReturn(Optional.of(deluxeRoomTest));

        List<Room> byRoomTypeDeluxe = this.mockRoomRepository.findByRoomType(RoomType.DELUXE);
        Room byRoomNumber = this.mockRoomRepository.findByRoomNumber(DELUXE_ROOM_NUMBER);
        Room roomById = this.mockRoomRepository.findById(DELUXE_ROOM_ID).get();

        Assertions.assertEquals(1, byRoomTypeDeluxe.size());
        Assertions.assertEquals(deluxeRoomTest.getRoomNumber(), byRoomTypeDeluxe.get(0).getRoomNumber());

        Assertions.assertEquals(byRoomNumber, deluxeRoomTest);

        Assertions.assertEquals(roomById, deluxeRoomTest);

    }

    @Test
    void rightPremiumRoomIsReturnedByTypeNumberAndId(){

        when(mockRoomRepository.findByRoomType(RoomType.PREMIUM))
                .thenReturn(List.of(premiumRoomTest));
        when(mockRoomRepository.findByRoomNumber(PREMIUM_ROOM_NUMBER))
                .thenReturn(premiumRoomTest);
        when(mockRoomRepository.findById(PREMIUM_ROOM_ID))
                .thenReturn(Optional.of(premiumRoomTest));

        List<Room> byRoomTypePremium = this.mockRoomRepository.findByRoomType(RoomType.PREMIUM);
        Room byRoomNumber = this.mockRoomRepository.findByRoomNumber(PREMIUM_ROOM_NUMBER);
        Room roomById = this.mockRoomRepository.findById(PREMIUM_ROOM_ID).get();

        Assertions.assertEquals(1, byRoomTypePremium.size());
        Assertions.assertEquals(premiumRoomTest.getRoomNumber(), byRoomTypePremium.get(0).getRoomNumber());

        Assertions.assertEquals(byRoomNumber, premiumRoomTest);

        Assertions.assertEquals(roomById, premiumRoomTest);

    }

    @Test
    void rightStudioRoomIsReturnedByTypeNumberAndId(){

        when(mockRoomRepository.findByRoomType(RoomType.STUDIO))
                .thenReturn(List.of(studioRoomTest));
        when(mockRoomRepository.findByRoomNumber(STUDIO_ROOM_NUMBER))
                .thenReturn(studioRoomTest);
        when(mockRoomRepository.findById(STUDIO_ROOM_ID))
                .thenReturn(Optional.of(studioRoomTest));

        List<Room> byRoomTypeStudio = this.mockRoomRepository.findByRoomType(RoomType.STUDIO);
        Room byRoomNumber = this.mockRoomRepository.findByRoomNumber(STUDIO_ROOM_NUMBER);
        Room roomById = this.mockRoomRepository.findById(STUDIO_ROOM_ID).get();

        Assertions.assertEquals(1, byRoomTypeStudio.size());
        Assertions.assertEquals(studioRoomTest.getRoomNumber(), byRoomTypeStudio.get(0).getRoomNumber());

        Assertions.assertEquals(byRoomNumber, studioRoomTest);

        Assertions.assertEquals(roomById, studioRoomTest);

    }

    @Test
    void rightApartmentRoomIsReturnedByTypeNumberAndId(){

        when(mockRoomRepository.findByRoomType(RoomType.APARTMENT))
                .thenReturn(List.of(apartmentRoomTest));
        when(mockRoomRepository.findByRoomNumber(APARTMENT_ROOM_NUMBER))
                .thenReturn(apartmentRoomTest);
        when(mockRoomRepository.findById(APARTMENT_ROOM_ID))
                .thenReturn(Optional.of(apartmentRoomTest));

        List<Room> byRoomTypeApartment = this.mockRoomRepository.findByRoomType(RoomType.APARTMENT);
        Room byRoomNumber = this.mockRoomRepository.findByRoomNumber(APARTMENT_ROOM_NUMBER);
        Room roomById = this.mockRoomRepository.findById(APARTMENT_ROOM_ID).get();

        Assertions.assertEquals(1, byRoomTypeApartment.size());
        Assertions.assertEquals(apartmentRoomTest.getRoomNumber(), byRoomTypeApartment.get(0).getRoomNumber());

        Assertions.assertEquals(byRoomNumber, apartmentRoomTest);

        Assertions.assertEquals(roomById, apartmentRoomTest);

    }

    @Test
    void rightVIPRoomIsReturnedByTypeNumberAndId(){

        when(mockRoomRepository.findByRoomType(RoomType.VIP))
                .thenReturn(List.of(vipRoomTest));
        when(mockRoomRepository.findByRoomNumber(VIP_ROOM_NUMBER))
                .thenReturn(vipRoomTest);
        when(mockRoomRepository.findById(VIP_ROOM_ID))
                .thenReturn(Optional.of(vipRoomTest));

        List<Room> byRoomTypeApartment = this.mockRoomRepository.findByRoomType(RoomType.VIP);
        Room byRoomNumber = this.mockRoomRepository.findByRoomNumber(VIP_ROOM_NUMBER);
        Room roomById = this.mockRoomRepository.findById(VIP_ROOM_ID).get();

        Assertions.assertEquals(1, byRoomTypeApartment.size());
        Assertions.assertEquals(vipRoomTest.getRoomNumber(), byRoomTypeApartment.get(0).getRoomNumber());

        Assertions.assertEquals(byRoomNumber, vipRoomTest);

        Assertions.assertEquals(roomById, vipRoomTest);

    }


    @Test
    void rightPresidentRoomIsReturnedByTypeNumberAndId(){

        when(mockRoomRepository.findByRoomType(RoomType.PRESIDENT))
                .thenReturn(List.of(presidentRoomTest));
        when(mockRoomRepository.findByRoomNumber(PRESIDENT_ROOM_NUMBER))
                .thenReturn(presidentRoomTest);
        when(mockRoomRepository.findById(PRESIDENT_ROOM_ID))
                .thenReturn(Optional.of(presidentRoomTest));

        List<Room> byRoomTypeApartment = this.mockRoomRepository.findByRoomType(RoomType.PRESIDENT);
        Room byRoomNumber = this.mockRoomRepository.findByRoomNumber(PRESIDENT_ROOM_NUMBER);
        Room roomById = this.mockRoomRepository.findById(PRESIDENT_ROOM_ID).get();

        Assertions.assertEquals(1, byRoomTypeApartment.size());
        Assertions.assertEquals(presidentRoomTest.getRoomNumber(), byRoomTypeApartment.get(0).getRoomNumber());

        Assertions.assertEquals(byRoomNumber, presidentRoomTest);

        Assertions.assertEquals(roomById, presidentRoomTest);

    }


}
