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
    private ReservationRoomDetails roomDetails;

    @ManyToOne(optional = false)
    private Payment payment;

    @Column(nullable = false)
    private BigDecimal totalValue;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    private UserEntity guest;

    public Reservation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ReservationRoomDetails getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(ReservationRoomDetails roomDetails) {
        this.roomDetails = roomDetails;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UserEntity getGuest() {
        return guest;
    }

    public void setGuest(UserEntity guest) {
        this.guest = guest;
    }


}
