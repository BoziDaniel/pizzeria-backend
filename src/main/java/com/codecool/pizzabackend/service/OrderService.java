package com.codecool.pizzabackend.service;

import com.codecool.pizzabackend.controller.IncomingOrderController;
import com.codecool.pizzabackend.controller.dto.IncomingOrderDTO;
import com.codecool.pizzabackend.controller.dto.PizzaQuantityDTO;
import com.codecool.pizzabackend.entity.*;
import com.codecool.pizzabackend.repository.IncomingOrderRepository;
import com.codecool.pizzabackend.repository.PizzaRepository;
import com.codecool.pizzabackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class OrderService {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IncomingOrderRepository incomingOrderRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    public void persistIncomingOrder(Long userId, IncomingOrderDTO incomingOrderDTO) {
        LOGGER.trace("Starting to create incoming order from userId: " + userId + "incomingOrderDTO: " + incomingOrderDTO.toString());
        HashMap<Pizza, Integer> pizzas = new HashMap<>();
        for (PizzaQuantityDTO orderedPizza : incomingOrderDTO.getOrderedPizzas()) {
            pizzas.put(pizzaRepository.getPizzaById(orderedPizza.getPizzaId()), orderedPizza.getQuantity());
        }
        User customer = userRepository.getUserById(userId);
        IncomingOrder incomingOrder = IncomingOrder.builder()
                .customer((Customer) customer)
                .orderStatus(OrderStatus.ORDERED)
                .orderedPizzas(pizzas)
                .build();

        LOGGER.info("Incoming order created. incoming order: " + incomingOrder.toString());
        incomingOrderRepository.save(incomingOrder);
        LOGGER.info("Incoming order persisted to db. incoming order: " + incomingOrder.toString());
    }
}


