package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.entity.Pizza;
import com.codecool.pizzabackend.repository.PizzaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pizzas")
//@CrossOrigin(origins = "http://localhost:3000")
@Component
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PizzaController.class);

    @GetMapping
    public List<Pizza> getPaginatedPizzas() {
        LOGGER.info("get mapping: /pizzas rest end point");
        List<Pizza> pizzas =  pizzaRepository.getPaginatedPizzas(1);
        return pizzas;
    }
}
