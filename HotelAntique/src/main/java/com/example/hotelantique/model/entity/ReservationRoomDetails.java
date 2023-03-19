package com.example.hotelantique.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservation_room_details")
public class ReservationRoomDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    @ManyToOne(optional = false)
    private Room room;

    @Column(nullable = false)
    private LocalDate arriving;

    @Column(nullable = false)
    private LocalDate departing;

    public ReservationRoomDetails() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getArriving() {
        return arriving;
    }

    public void setArriving(LocalDate arriving) {
        this.arriving = arriving;
    }

    public LocalDate getDeparting() {
        return departing;
    }

    public void setDeparting(LocalDate departing) {
        this.departing = departing;
    }



}
