package com.example.hotelantique.model.dtos.reservationDTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ReservationDTO {

    @Size(min = 3, max = 20)
    @NotEmpty
    private String fullName;

    @NotEmpty
    private String roomType;

    @FutureOrPresent
    private LocalDate checkIn;

    @Future
    private LocalDate checkOut;

    @Email
    private String email;

    @Size(min = 10)
    @NotEmpty
    private String phoneNumber;

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

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialWishes() {
        return specialWishes;
    }

    public void setSpecialWishes(String specialWishes) {
        this.specialWishes = specialWishes;
    }
}
