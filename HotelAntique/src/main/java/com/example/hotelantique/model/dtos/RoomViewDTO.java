package com.example.hotelantique.model.dtos;

import java.math.BigDecimal;

public class RoomViewDTO {

    private long id;

    private String name;
    private BigDecimal price;

    private String type;

    public RoomViewDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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



}
