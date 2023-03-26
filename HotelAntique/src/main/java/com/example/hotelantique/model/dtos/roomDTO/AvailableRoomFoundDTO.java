package com.example.hotelantique.model.dtos.roomDTO;

public class AvailableRoomFoundDTO {
    private long id;
    private String name;
    private int roomNumber;

    public AvailableRoomFoundDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
