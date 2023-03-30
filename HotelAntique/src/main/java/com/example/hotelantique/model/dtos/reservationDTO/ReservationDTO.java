package com.example.hotelantique.model.dtos.reservationDTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ReservationDTO {

    @Size(min = 3, max = 20)
    @NotEmpty
    private String fullName;

    @NotEmpty
    private String roomType;


    private String checkIn;


    private String checkOut;

    @Email
    private String email;


    private String specialWishes;

    public ReservationDTO() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getSpecialWishes() {
        return specialWishes;
    }

    public void setSpecialWishes(String specialWishes) {
        this.specialWishes = specialWishes;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "fullName='" + fullName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", email='" + email + '\'' +
                ", specialWishes='" + specialWishes + '\'' +
                '}';
    }
}
