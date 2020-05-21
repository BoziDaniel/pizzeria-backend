package com.codecool.pizzabackend.controller.dto;

import com.codecool.pizzabackend.entity.Customer;
import com.codecool.pizzabackend.entity.IncomingOrder;
import com.codecool.pizzabackend.entity.OrderStatus;
import com.codecool.pizzabackend.entity.Pizza;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.swing.plaf.ActionMapUIResource;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomingOrderDTO {
    private Long id;
    private OrderStatus orderStatus;
    private Customer customer;

    List<PizzaQuantityDTO> incomingOrderedPizzas;

    public IncomingOrderDTO(List<PizzaQuantityDTO> orderedPizzas) {
        this.incomingOrderedPizzas = orderedPizzas;
    }

    public IncomingOrderDTO(Long id, OrderStatus orderStatus) {
        this.id = id;
        this.orderStatus = orderStatus;

    }




}
