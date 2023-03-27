package com.example.hotelantique.model.dtos.reservationDTO;

import com.example.hotelantique.model.entity.Room;

import java.time.LocalDate;

public class ReservationDetailsDTO {

    private long id;

    private String roomType;
    private String guestUsername;

    private LocalDate checkIn;
    private LocalDate checkOut;

    private boolean isActive;

    public ReservationDetailsDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getGuestUsername() {
        return guestUsername;
    }

    public void setGuestUsername(String guestUsername) {
        this.guestUsername = guestUsername;
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

    public String reservationStatus(){
        return isActive ? "ACTIVE" : "ANULATED";
    }
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "ReservationDetailsDTO{" +
                "id=" + id +
                ", roomType='" + roomType + '\'' +
                ", guestUsername='" + guestUsername + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", isActive=" + isActive +
                '}';
    }
}
