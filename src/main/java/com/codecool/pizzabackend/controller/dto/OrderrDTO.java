package com.codecool.pizzabackend.controller.dto;

import com.codecool.pizzabackend.entity.Cook;
import com.codecool.pizzabackend.entity.Customer;
import com.codecool.pizzabackend.entity.DeliveryGuy;
import com.codecool.pizzabackend.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderrDTO {
    private Long id;
    private OrderStatus orderStatus;
    private Customer customer;
    private Cook cook;
    private DeliveryGuy deliveryGuy;
    List<PizzaQuantityDTO> incomingOrderedPizzas;

    public OrderrDTO(List<PizzaQuantityDTO> orderedPizzas) {
        this.incomingOrderedPizzas = orderedPizzas;
    }

    public OrderrDTO(Long id, OrderStatus orderStatus, Cook cook, DeliveryGuy deliveryGuy) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.cook = cook;
        this.deliveryGuy = deliveryGuy;

    }


}
