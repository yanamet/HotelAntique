package com.example.hotelantique.model.dtos.reservationDTO;

public class ReservationViewDTO {
    private long id;
    private String guestUsername;
    private String guestFullName;
    private String checkInAndCheckOut;
    private boolean isActive;

    public ReservationViewDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGuestUsername() {
        return guestUsername;
    }

    public void setGuestUsername(String guestUsername) {
        this.guestUsername = guestUsername;
    }

    public String getGuestFullName() {
        return guestFullName;
    }

    public void setGuestFullName(String guestFullName) {
        this.guestFullName = guestFullName;
    }

    public String getCheckInAndCheckOut() {
        return checkInAndCheckOut;
    }

    public void setCheckInAndCheckOut(String checkInAndCheckOut) {
        this.checkInAndCheckOut = checkInAndCheckOut;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
