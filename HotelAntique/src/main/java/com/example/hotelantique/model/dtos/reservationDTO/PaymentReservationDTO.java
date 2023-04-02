package com.example.hotelantique.model.dtos.reservationDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PaymentReservationDTO {

    private long reservationId;


    @Size(min = 5)
    @NotEmpty
    private String owner;

    @Size(max = 3)
    @NotEmpty
    private String cvv;

    @NotEmpty
    private String expirationMonth;

    @Min(2023)
    private int expirationYear;

    @Size(min = 5)
    @NotEmpty
    private String cardNumber;

    public PaymentReservationDTO() {
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "PaymentReservationDTO{" +
                "owner='" + owner + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expirationMonth='" + expirationMonth + '\'' +
                ", expirationYear=" + expirationYear +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
