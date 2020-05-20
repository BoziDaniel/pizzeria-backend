package com.codecool.pizzabackend.controller.dto;

import com.codecool.pizzabackend.entity.Pizza;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaQuantityDTO {
    private Long id;
    private String name;
    private String description;
    private int quantity;


}
