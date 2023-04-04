package com.example.hotelantique.model.dtos.roomDTO;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class RoomViewDTO {

    private long id;

    private String name;
    private BigDecimal price;

    private String type;

    private List<String> description;

    public RoomViewDTO() {
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(String descriptionString) {
        String[] split = descriptionString.split(", ");
        this.description = Arrays.stream(split).toList();
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
