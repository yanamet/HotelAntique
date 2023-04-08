package com.example.hotelantique.model.dtos.roomDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AvailableRoomSearchDTO {

    @FutureOrPresent(message = "Check-in date must be in the present or in the future.")
    @NotNull
    private LocalDate checkIn;

    @Future(message = "Check-out date must be in the future.")
    @NotNull
    private LocalDate checkOut;

    @NotEmpty(message = "You must select a room type.")
    private String roomType;

    public AvailableRoomSearchDTO() {
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

    @Override
    public String toString() {
        return "AvailableRoomSearchDTO{" +
                "checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", roomType='" + roomType + '\'' +
                '}';
    }
}

