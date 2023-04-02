package com.example.hotelantique.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    private Room room;

    @ManyToOne(optional = false)
    private UserEntity guest;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;


    @Column(nullable = false)
    private BigDecimal totalValue;

    @Column(nullable = false)
    private LocalDate createdOn;

    private boolean isActive;

    public Reservation() {
    }





    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate date) {
        this.createdOn = date;
    }

    public UserEntity getGuest() {
        return guest;
    }

    public void setGuest(UserEntity guest) {
        this.guest = guest;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate arriving) {
        this.checkIn = arriving;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate departing) {
        this.checkOut = departing;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", room=" + room +
                ", guest=" + guest +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +

                ", totalValue=" + totalValue +
                ", createdOn=" + createdOn +
                ", isActive=" + isActive +
                '}';
    }

}
