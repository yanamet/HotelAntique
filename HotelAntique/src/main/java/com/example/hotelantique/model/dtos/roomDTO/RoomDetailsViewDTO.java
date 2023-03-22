package com.example.hotelantique.model.dtos.roomDTO;

import java.math.BigDecimal;

public class RoomDetailsViewDTO {

    private String name;

    private BigDecimal price;

    private String type;

    public RoomDetailsViewDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
