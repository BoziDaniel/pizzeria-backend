package com.codecool.pizzabackend.controller.dto;

import lombok.Data;

@Data
public class PizzaQuantityDTO {
    private Long pizzaId;
    private int quantity;
}
