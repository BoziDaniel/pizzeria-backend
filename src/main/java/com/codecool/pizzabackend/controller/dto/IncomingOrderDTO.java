package com.codecool.pizzabackend.controller.dto;

import com.codecool.pizzabackend.entity.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class IncomingOrderDTO {
    OrderStatus orderStatus;
    List<PizzaQuantityDTO> orderedPizzas;
}
